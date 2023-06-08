package com.gedoge.armor.autoconfigure

import com.gedoge.armor.autoconfigure.EventStoreDriver.*
import com.gedoge.armor.autoconfigure.OutboundEventProcessorType.*
import com.gedoge.armor.core.domain.DomainEventPublisher
import com.gedoge.armor.core.impl.inbound.CombinedDomainEventConverter
import com.gedoge.armor.core.impl.inbound.PersistenceAndRepublishProcessor
import com.gedoge.armor.core.impl.outbound.CombinedPublishListener
import com.gedoge.armor.core.impl.outbound.PersistenceEventProcessor
import com.gedoge.armor.core.impl.outbound.PersistenceStatusListener
import com.gedoge.armor.core.impl.outbound.scheduler.TryPublishAfterTransactionProcessor
import com.gedoge.armor.core.impl.outbound.scheduler.quartz.OutboundEventSchedulingJob
import com.gedoge.armor.core.impl.store.jdbc.JdbcEventStore
import com.gedoge.armor.core.impl.store.jdbc.MSSQLDriverDelegate
import com.gedoge.armor.core.impl.store.jdbc.MYSQLDriverDelegate
import com.gedoge.armor.core.inbound.InboundEventProcessor
import com.gedoge.armor.core.outbound.CombinedSourceSerializer
import com.gedoge.armor.core.outbound.OutboundEventInterceptor
import com.gedoge.armor.core.outbound.OutboundEventProcessor
import com.gedoge.armor.core.spi.DomainEventConverter
import com.gedoge.armor.core.spi.EventPublisher
import com.gedoge.armor.core.spi.PublishListener
import com.gedoge.armor.core.spi.SourceSerializer
import com.gedoge.armor.core.store.EventStore
import com.gedoge.armor.core.utils.*
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.*
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.JobDetailFactoryBean
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean
import javax.sql.DataSource

@Configuration
class ArmorAutoConfiguration {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun domainEventPublisher(): DomainEventPublisher {
        logger.info("create domainEventPublisher")
        return DomainEventPublisher()
    }

    @Configuration
    @AutoConfigureAfter(value = [DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class, LiquibaseAutoConfiguration::class, FlywayAutoConfiguration::class, TransactionAutoConfiguration::class])
    @ConditionalOnProperty(prefix = "gedoge.armor", name = ["enabled"], havingValue = "true")
    @EnableConfigurationProperties(ArmorProperties::class)
    @ComponentScan(basePackageClasses = [SourceSerializer::class, PublishListener::class, DomainEventConverter::class])
    class BasicConfiguration {
        private val logger = LoggerFactory.getLogger(javaClass)

        @Bean
        fun eventIdGenerator(): EventIdGenerator {
            return UUIDGenerator()
        }

        @Bean
        fun eventJsonConverter(): JsonConverter {
            return JacksonConverter()
        }

        @Bean
        fun eventStore(
            armorProperties: ArmorProperties,
            dataSource: DataSource,
        ): EventStore {
            logger.info("create eventStore")
            EventUtils.initialize(eventIdGenerator(), eventJsonConverter())
            val eventStoreProperties = armorProperties.store
            return when (eventStoreProperties.type) {
                EventStoreType.JDBC -> {
                    val jdbcProperty = eventStoreProperties.jdbc!!
                    val driverDelegate = when (jdbcProperty.driver) {
                        MYSQL -> MYSQLDriverDelegate(jdbcProperty.outboundSchema, jdbcProperty.inboundSchema)
                        MSSQL -> MSSQLDriverDelegate(jdbcProperty.outboundSchema, jdbcProperty.inboundSchema)
                    }
                    JdbcEventStore(dataSource, driverDelegate)
                }
            }
        }

        @ConditionalOnProperty(prefix = "gedoge.armor.outbound", name = ["processor"], havingValue = "NONE")
        @Bean
        fun persistenceEventProcessor(
            eventStore: EventStore,
        ): OutboundEventProcessor {
            logger.info("create persistenceEventProcessor")
            return PersistenceEventProcessor(eventStore)
        }

        @ConditionalOnProperty(prefix = "gedoge.armor.outbound", name = ["processor"], havingValue = "SCHEDULER")
        @Bean
        fun tryPublishAfterTransactionProcessor(
            armorProperties: ArmorProperties,
            eventStore: EventStore,
            eventPublisher: EventPublisher
        ): OutboundEventProcessor {
            logger.info("create outboundEventProcessor")
            return TryPublishAfterTransactionProcessor(eventPublisher, eventStore)
        }

        @Bean
        fun combinedSourceSerializer(sourceSerializer: List<SourceSerializer>): CombinedSourceSerializer {
            return CombinedSourceSerializer(sourceSerializer)
        }

        @Bean
        fun outboundEventInterceptor(
            outboundEventProcessor: OutboundEventProcessor,
            combinedSourceSerializer: CombinedSourceSerializer,
        ): OutboundEventInterceptor {
            logger.info("create outboundEventHandler")
            return OutboundEventInterceptor(outboundEventProcessor, combinedSourceSerializer)
        }

        @Bean
        fun persistenceStatusListener(eventStore: EventStore): PublishListener {
            return PersistenceStatusListener(eventStore)
        }

        @Bean
        fun combinedPublishListener(
            eventStore: EventStore,
            publishListeners: List<PublishListener>
        ): CombinedPublishListener {
            logger.info("create combinedPublishListener")
            return CombinedPublishListener(eventStore, publishListeners)
        }

        @Bean
        fun combinedDomainEventConverter(domainEventConverters: List<DomainEventConverter>): CombinedDomainEventConverter {
            return CombinedDomainEventConverter(domainEventConverters)
        }

        @Bean
        fun persistenceAndRepublishProcessor(
            combinedDomainEventConverter: CombinedDomainEventConverter,
            eventStore: EventStore,
            domainEventPublisher: DomainEventPublisher
        ): InboundEventProcessor {
            return PersistenceAndRepublishProcessor(combinedDomainEventConverter, eventStore, domainEventPublisher)
        }

    }

    @Configuration
    @AutoConfigureAfter(value = [BasicConfiguration::class])
    @ConditionalOnExpression("'\${gedoge.armor.enabled:false}' and '\${gedoge.armor.outbound.processor:NONE}' == 'SCHEDULER'")
    class OutboundEventSchedulerProcessorConfiguration {
        private val logger = LoggerFactory.getLogger(javaClass)

        @Bean
        fun outboundEventSchedulingJobDetail(armorProperties: ArmorProperties): JobDetailFactoryBean {
            logger.info("create outboundEventSchedulingJobDetail")
            val schedulerProperties = armorProperties.outbound.scheduler!!
            val jobDetailFactoryBean = JobDetailFactoryBean()
            jobDetailFactoryBean.setName(schedulerProperties.jobName)
            jobDetailFactoryBean.setGroup(schedulerProperties.jobGroup)
            jobDetailFactoryBean.setJobClass(OutboundEventSchedulingJob::class.java)
            jobDetailFactoryBean.setDurability(true)
            return jobDetailFactoryBean
        }

        @Bean
        fun outboundEventSchedulingTrigger(
            armorProperties: ArmorProperties,
            routeEventJobDetail: JobDetailFactoryBean
        ): SimpleTriggerFactoryBean {
            logger.info("create outboundEventSchedulingTrigger")
            val schedulerProperties = armorProperties.outbound.scheduler!!
            val simpleTriggerFactoryBean = SimpleTriggerFactoryBean()
            simpleTriggerFactoryBean.setName(schedulerProperties.triggerName)
            simpleTriggerFactoryBean.setGroup(schedulerProperties.jobGroup)
            simpleTriggerFactoryBean.setJobDetail(routeEventJobDetail.`object`!!)
            simpleTriggerFactoryBean.setStartDelay(schedulerProperties.startDelay.toLong())
            simpleTriggerFactoryBean.setRepeatInterval(schedulerProperties.repeatInterval.toLong())
            return simpleTriggerFactoryBean
        }

    }

}

