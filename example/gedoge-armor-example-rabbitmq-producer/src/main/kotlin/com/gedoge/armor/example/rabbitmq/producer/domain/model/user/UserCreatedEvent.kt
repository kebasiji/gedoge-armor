package com.gedoge.armor.example.rabbitmq.producer.domain.model.user

import com.gedoge.armor.core.domain.DomainEvent
import com.gedoge.armor.core.outbound.Outbound
import com.gedoge.armor.example.rabbitmq.producer.infrastructure.configuration.constants.EVENT_TYPE_USER_CREATED
import com.gedoge.armor.example.rabbitmq.producer.infrastructure.configuration.constants.EXCHANGE_ARMOR_EXAMPLE_USER

@Outbound(type = EVENT_TYPE_USER_CREATED, destination = EXCHANGE_ARMOR_EXAMPLE_USER, tag = "created")
data class UserCreatedEvent(private val user: User) : DomainEvent(user) {
    override val sourceId: String
        get() = user.id.toString()

}