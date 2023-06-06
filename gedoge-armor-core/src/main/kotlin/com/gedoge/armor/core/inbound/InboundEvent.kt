package com.gedoge.armor.core.inbound

import com.gedoge.armor.core.domain.Payload
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class InboundEvent(
    val eventId: String,
    val occurredAt: LocalDateTime,
    val type: String,
    val sourceId: String,
    val source: String,
    val payload: Payload? = null,
) {

    val arrivedAt: LocalDateTime = LocalDateTime.now()

}