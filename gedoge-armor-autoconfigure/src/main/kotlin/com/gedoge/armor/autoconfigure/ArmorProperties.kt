package com.gedoge.armor.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("gedoge.armor")
data class ArmorProperties(
    val enabled: Boolean = false,
    val store: EventStoreProperties = EventStoreProperties(),
    val outbound: OutboundEventProperties = OutboundEventProperties()
)

@ConstructorBinding
data class EventStoreProperties(
    val type: EventStoreType = EventStoreType.JDBC,
    val jdbc: JdbcEventStoreProperties? = null
)

@ConstructorBinding
data class JdbcEventStoreProperties(
    val driver: EventStoreDriver = EventStoreDriver.MSSQL,
    val outboundSchema: String = "outbound_event",
    val inboundSchema: String = "inbound_event"
)

enum class EventStoreType {
    JDBC
}

enum class EventStoreDriver {
    MYSQL,
    MSSQL
}

@ConstructorBinding
data class OutboundEventProperties(
    val processor: OutboundEventProcessorType = OutboundEventProcessorType.SCHEDULER,
    val scheduler: OutboundEventSchedulerProcessorProperties? = null
)

enum class OutboundEventProcessorType {
    NONE,
    SCHEDULER,
}

@ConstructorBinding
data class OutboundEventSchedulerProcessorProperties(
    val startDelay: String = 1000.toString(),
    val repeatInterval: String = 5000.toString(),
    val jobName: String = "outboundEventSchedulingJob",
    val jobGroup: String = "outboundEvent",
    val triggerName: String = "outboundEventSchedulingTrigger",
)






