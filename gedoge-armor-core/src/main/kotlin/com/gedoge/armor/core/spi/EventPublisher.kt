package com.gedoge.armor.core.spi

import com.gedoge.armor.core.outbound.OutboundEvent

interface EventPublisher {

    fun publish(event: OutboundEvent)

}