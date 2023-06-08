package com.gedoge.armor.example.rabbitmq.producer.api.http.user

import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.User

data class UpdateUserAC(
    val name: String
)

data class AddUserAC(
    val name: String
)

data class UserAS(
    val id: Long,
    val name: String
)

fun User.toUserAS(): UserAS {
    return UserAS(id = id!!, name = name)
}