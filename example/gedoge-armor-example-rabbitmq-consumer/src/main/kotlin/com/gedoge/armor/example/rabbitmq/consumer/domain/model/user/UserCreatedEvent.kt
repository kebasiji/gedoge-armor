package com.gedoge.armor.example.rabbitmq.consumer.domain.model.user

import com.gedoge.armor.core.domain.DomainEvent

class UserCreatedEvent(private val user: User) : DomainEvent(user) {
    override val sourceId: String
        get() = user.id.toString()

}