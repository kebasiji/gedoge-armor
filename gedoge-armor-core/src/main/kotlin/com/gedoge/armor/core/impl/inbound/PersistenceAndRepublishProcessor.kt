package com.gedoge.armor.core.impl.inbound

import com.gedoge.armor.core.domain.DomainEventPublisher
import com.gedoge.armor.core.store.InboundEventStore
import com.gedoge.armor.core.inbound.InboundEventProcessor
import com.gedoge.armor.core.outbound.Outbound
import com.gedoge.armor.core.utils.EventUtils
import org.springframework.transaction.annotation.Transactional

@Transactional
class PersistenceAndRepublishProcessor(
    private val combinedDomainEventConverter: CombinedDomainEventConverter,
    private val eventStore: InboundEventStore,
    private val domainEventPublisher: DomainEventPublisher,
) : InboundEventProcessor {

    override fun process(eventJsonString: String) {
        val inboundEvent = EventUtils.jsonStringToEvent(eventJsonString)
        val domainEvent = combinedDomainEventConverter.convert(inboundEvent)
        val clazz = domainEvent::class.java
        val outbound = clazz.getDeclaredAnnotation(Outbound::class.java)
        if (outbound != null) {
            throw RuntimeException("can not republish a outbound event $clazz")
        }
        domainEvent.eventId = inboundEvent.eventId
        if (eventStore.saveIfAbsent(inboundEvent)) {
            domainEventPublisher.publish(domainEvent)
        }
    }

}