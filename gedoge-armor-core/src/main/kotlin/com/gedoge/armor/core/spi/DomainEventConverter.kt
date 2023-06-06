package com.gedoge.armor.core.spi

import com.gedoge.armor.core.domain.DomainEvent
import com.gedoge.armor.core.inbound.InboundEvent

interface DomainEventConverter {

    fun getSupportedEventType(): Set<String>

    fun isSupportedEventType(type: String): Boolean {
        return getSupportedEventType().contains(type)
    }

    fun convert(event: InboundEvent): DomainEvent

}