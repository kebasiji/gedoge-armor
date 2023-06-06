package com.gedoge.armor.core.impl.outbound

import com.gedoge.armor.core.outbound.OutboundEvent
import com.gedoge.armor.core.spi.PublishListener
import com.gedoge.armor.core.store.OutboundEventStore
import org.slf4j.LoggerFactory

class CombinedPublishListener(
    private val eventStore: OutboundEventStore,
    private val publishListeners: List<PublishListener>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun onSent(eventId: Long) {
        val event = eventStore.findById(eventId)
        if (event != null) {
            onSent(event)
        } else {
            logger.error("sent event is not exist {}", eventId)
        }
    }

    fun onSent(event: OutboundEvent) {
        for (publishListener in publishListeners) {
            publishListener.onSent(event)
        }
    }

}