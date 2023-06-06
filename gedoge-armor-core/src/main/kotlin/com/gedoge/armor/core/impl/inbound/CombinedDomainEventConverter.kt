package com.gedoge.armor.core.impl.inbound

import com.gedoge.armor.core.domain.DomainEvent
import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.spi.DomainEventConverter

class CombinedDomainEventConverter(private val domainEventConverters: List<DomainEventConverter>) {

    fun convert(event: InboundEvent): DomainEvent {
        return findAppropriateConverter(event.type).convert(event)
    }

    private fun findAppropriateConverter(type: String): DomainEventConverter {
        return domainEventConverters.find { parser -> parser.isSupportedEventType(type) }
            ?: throw RuntimeException("can not find appropriate DomainEventConverter")
    }

}