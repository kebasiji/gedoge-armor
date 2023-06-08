package com.gedoge.armor.example.rabbitmq.consumer.domain.model.user

import com.gedoge.armor.core.domain.DomainEvent

data class UserUpdatedEvent(private val user: User) : DomainEvent(user) {

    override val sourceId: String
        get() = user.id.toString()

}