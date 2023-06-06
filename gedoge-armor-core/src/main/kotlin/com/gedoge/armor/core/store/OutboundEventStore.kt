package com.gedoge.armor.core.store

import com.gedoge.armor.core.outbound.OutboundEvent

interface OutboundEventStore {

    fun save(event: OutboundEvent)

    fun findById(eventId: Long): OutboundEvent?

    fun listWaitingEvent(): List<OutboundEvent>

    fun update(event: OutboundEvent)

}