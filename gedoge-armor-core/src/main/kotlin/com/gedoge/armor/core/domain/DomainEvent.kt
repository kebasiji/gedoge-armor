package com.gedoge.armor.core.domain

import java.time.LocalDateTime

abstract class DomainEvent(
    val source: Any,
    val payload: Payload? = null,
) {

    var eventId: String? = null

    val occurredAt: LocalDateTime = LocalDateTime.now()

    abstract val sourceId: String

}