package com.gedoge.armor.core.utils

import com.gedoge.armor.core.domain.Payload
import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.outbound.OutboundEvent

interface JsonConverter {

    fun outboundEventToJsonString(event: OutboundEvent): String

    fun jsonStringToInboundEvent(jsonString: String): InboundEvent

    fun jsonStringToPayload(jsonString: String): Payload

    fun payloadToJsonString(payload: Payload): String

    fun sourceToJsonString(source: Any): String

}