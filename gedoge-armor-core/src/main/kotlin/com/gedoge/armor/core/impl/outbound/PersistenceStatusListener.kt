package com.gedoge.armor.core.impl.outbound

import com.gedoge.armor.core.outbound.OutboundEvent
import com.gedoge.armor.core.spi.PublishListener
import com.gedoge.armor.core.store.OutboundEventStore


class PersistenceStatusListener(private val eventStore: OutboundEventStore) : PublishListener {

    override fun onSent(event: OutboundEvent) {
        if (event.changeStatusOnSent()) {
            eventStore.update(event)
        }
    }

}