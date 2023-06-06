package com.gedoge.armor.core.utils

import com.gedoge.armor.core.domain.Payload
import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.outbound.OutboundEvent

object EventUtils {
    private lateinit var eventIdGenerator: EventIdGenerator
    private lateinit var jsonGenerator: JsonConverter

    fun initialize(eventIdGenerator: EventIdGenerator, jsonGenerator: JsonConverter) {
        EventUtils.eventIdGenerator = eventIdGenerator
        EventUtils.jsonGenerator = jsonGenerator
    }

    fun newEventId(): String {
        return eventIdGenerator.generateId()
    }

    fun eventToJsonString(event: OutboundEvent): String {
        return jsonGenerator.outboundEventToJsonString(event)
    }

    fun jsonStringToEvent(jsonString: String): InboundEvent {
        return jsonGenerator.jsonStringToInboundEvent(jsonString)
    }

    fun jsonStringToPayload(jsonString: String): Payload {
        return jsonGenerator.jsonStringToPayload(jsonString)
    }

    fun payloadToJsonString(payload: Payload): String {
        return jsonGenerator.payloadToJsonString(payload)
    }

    fun sourceToJsonString(source: Any): String {
        return jsonGenerator.sourceToJsonString(source)
    }

}

