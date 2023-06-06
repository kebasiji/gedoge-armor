package com.gedoge.armor.core.utils

import com.gedoge.armor.core.domain.Payload
import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.outbound.OutboundEvent
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JacksonConverter : JsonConverter {
    private val objectMapper: ObjectMapper

    init {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTimeDeserializer = LocalDateTimeDeserializer(dateTimeFormatter)
        val localDateTimeSerializer = LocalDateTimeSerializer(dateTimeFormatter)
        objectMapper = jacksonObjectMapper()
            .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(ParameterNamesModule())
            .registerModule(Jdk8Module())
            .registerModule(
                JavaTimeModule().addDeserializer(LocalDateTime::class.java, localDateTimeDeserializer)
                    .addSerializer(LocalDateTime::class.java, localDateTimeSerializer)
            )
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    override fun outboundEventToJsonString(event: OutboundEvent): String {
        return objectMapper.writeValueAsString(event)
    }

    override fun jsonStringToInboundEvent(jsonString: String): InboundEvent {
        return objectMapper.readValue(jsonString)
    }

    override fun jsonStringToPayload(jsonString: String): Payload {
        val map = objectMapper.readValue<Map<String, String>>(jsonString)
        return Payload(map)
    }

    override fun payloadToJsonString(payload: Payload): String {
        return objectMapper.writeValueAsString(payload.toMap())
    }

    override fun sourceToJsonString(source: Any): String {
        return objectMapper.writeValueAsString(source)
    }

}