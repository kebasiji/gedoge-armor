package com.gedoge.armor.core.domain

import com.gedoge.armor.core.domain.MutablePayload
import com.gedoge.armor.core.domain.Payload

fun mutablePayloadOf(vararg pairs: Pair<String, String>): MutablePayload {
    return MutablePayload(mutableMapOf(*pairs))
}

fun payloadOf(vararg pairs: Pair<String, String>): Payload {
    return Payload(mapOf(*pairs))
}

fun MutablePayload.toPayload(): Payload {
    return Payload(toMap())
}

fun Payload.toMutablePayload(): MutablePayload {
    return MutablePayload(toMutableMap())
}