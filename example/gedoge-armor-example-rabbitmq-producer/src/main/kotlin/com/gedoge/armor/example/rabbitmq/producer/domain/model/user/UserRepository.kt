package com.gedoge.armor.example.rabbitmq.producer.domain.model.user

interface UserRepository {

    fun save(user: User)

    fun findById(id: Long): User?

}