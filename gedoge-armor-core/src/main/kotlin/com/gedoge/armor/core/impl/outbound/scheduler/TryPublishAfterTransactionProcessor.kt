package com.gedoge.armor.core.impl.outbound.scheduler

import com.gedoge.armor.core.impl.outbound.PersistenceEventProcessor
import com.gedoge.armor.core.outbound.OutboundEvent
import com.gedoge.armor.core.spi.EventPublisher
import com.gedoge.armor.core.store.EventStore
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.transaction.event.TransactionalEventListener

class TryPublishAfterTransactionProcessor(
    private val eventPublisher: EventPublisher,
    eventStore: EventStore
) : PersistenceEventProcessor(eventStore), ApplicationEventPublisherAware {
    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    override fun process(event: OutboundEvent) {
        super.process(event)
        applicationEventPublisher.publishEvent(event)
    }

    @TransactionalEventListener
    fun onOutboundEvent(event: OutboundEvent) {
        try {
            eventPublisher.publish(event)
        } catch (ex: Exception) {
            logger.error("try publish event error", ex)
        }
    }

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher
    }

}