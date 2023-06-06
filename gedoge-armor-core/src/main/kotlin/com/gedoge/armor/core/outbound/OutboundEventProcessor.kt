package com.gedoge.armor.core.outbound

interface OutboundEventProcessor {

    fun process(event: OutboundEvent)

}