package com.gedoge.armor.core.impl.outbound

import com.gedoge.armor.core.outbound.OutboundEvent
import com.gedoge.armor.core.outbound.OutboundEventProcessor
import com.gedoge.armor.core.store.OutboundEventStore

open class PersistenceEventProcessor(protected val eventStore: OutboundEventStore) : OutboundEventProcessor {

    override fun process(event: OutboundEvent) {
        eventStore.save(event)
    }

}