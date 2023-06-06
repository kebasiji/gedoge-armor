package com.gedoge.armor.core.outbound

import com.gedoge.armor.core.utils.EventUtils
import com.fasterxml.jackson.annotation.JsonIgnore
import com.gedoge.armor.core.domain.DomainEvent
import com.gedoge.armor.core.domain.Payload
import java.time.LocalDateTime

class OutboundEvent internal constructor(
    val eventId: String,
    val occurredAt: LocalDateTime,
    val type: String,
    val sourceId: String,
    val source: String,
    val payload: Payload? = null,
    @JsonIgnore
    val destination: String,
    @JsonIgnore
    val tag: String?,
    @JsonIgnore
    private var _status: OutboundEventStatus,
    @JsonIgnore
    private var _sentAt: LocalDateTime?
) {

    val status: OutboundEventStatus
        get() = _status

    val sentAt: LocalDateTime?
        get() = _sentAt

    companion object {

        internal fun create(domainEvent: DomainEvent, source: String, outbound: Outbound): OutboundEvent {
            return OutboundEvent(
                eventId = domainEvent.eventId ?: EventUtils.newEventId(),
                occurredAt = domainEvent.occurredAt,
                type = outbound.type,
                sourceId = domainEvent.sourceId,
                source = source,
                payload = domainEvent.payload,
                destination = outbound.destination,
                tag = outbound.tag.ifBlank { null },
                _status = OutboundEventStatus.WAITING,
                _sentAt = null
            )
        }

    }

    internal fun changeStatusOnSent(): Boolean {
        if (_status == OutboundEventStatus.SENT) {
            return false
        }
        _status = OutboundEventStatus.SENT
        _sentAt = LocalDateTime.now()
        return true
    }

    fun toJsonString(): String {
        return EventUtils.eventToJsonString(this)
    }

    override fun toString(): String {
        return "OutboundEvent(eventId='$eventId', occurredAt=$occurredAt, type='$type', sourceId='$sourceId', source='$source', payload=$payload, destination='$destination', tag=$tag, status=$status, sentAt=$sentAt)"
    }

}

enum class OutboundEventStatus {
    WAITING,
    SENT
}