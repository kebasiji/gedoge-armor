package com.gedoge.armor.core.outbound

import com.gedoge.armor.core.domain.DomainEvent
import org.springframework.context.event.EventListener

class OutboundEventInterceptor(
    private val outboundEventProcessor: OutboundEventProcessor,
    private val combinedSourceSerializer: CombinedSourceSerializer,
) {

    @EventListener
    fun onDomainEvent(domainEvent: DomainEvent) {
        val clazz = domainEvent::class.java
        val outbound = clazz.getDeclaredAnnotation(Outbound::class.java) ?: return
        val event = createOutboundEvent(domainEvent, outbound)
        outboundEventProcessor.process(event)
    }

    private fun createOutboundEvent(domainEvent: DomainEvent, outbound: Outbound): OutboundEvent {
        return OutboundEvent.create(domainEvent, combinedSourceSerializer.serialize(domainEvent.source), outbound)
    }

}