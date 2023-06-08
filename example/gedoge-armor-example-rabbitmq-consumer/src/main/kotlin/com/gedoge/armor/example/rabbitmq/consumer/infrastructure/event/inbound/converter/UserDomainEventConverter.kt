package com.gedoge.armor.example.rabbitmq.consumer.infrastructure.event.inbound.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.gedoge.armor.core.domain.DomainEvent
import com.gedoge.armor.core.inbound.InboundEvent
import com.gedoge.armor.core.spi.DomainEventConverter
import com.gedoge.armor.example.rabbitmq.consumer.domain.model.user.User
import com.gedoge.armor.example.rabbitmq.consumer.domain.model.user.UserCreatedEvent
import com.gedoge.armor.example.rabbitmq.consumer.domain.model.user.UserUpdatedEvent
import org.springframework.stereotype.Component

@Component
class UserDomainEventConverter(private val objectMapper: ObjectMapper) : DomainEventConverter {

    override fun getSupportedEventType(): Set<String> {
        return setOf("userCreated", "userUpdated")
    }

    override fun convert(event: InboundEvent): DomainEvent {
        val user = objectMapper.readValue(event.source, User::class.java)
        return when (event.type) {
            "userCreated" -> UserCreatedEvent(user)
            "userUpdated" -> UserUpdatedEvent(user)
            else -> throw RuntimeException()
        }
    }

}