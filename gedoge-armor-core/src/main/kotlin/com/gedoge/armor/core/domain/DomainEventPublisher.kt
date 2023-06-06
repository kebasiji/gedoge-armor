package com.gedoge.armor.core.domain

import com.gedoge.armor.core.domain.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware

class DomainEventPublisher : ApplicationEventPublisherAware {
    private lateinit var eventPublisher: ApplicationEventPublisher

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        eventPublisher = applicationEventPublisher
    }

    fun publish(event: DomainEvent) {
        eventPublisher.publishEvent(event)
    }

}

