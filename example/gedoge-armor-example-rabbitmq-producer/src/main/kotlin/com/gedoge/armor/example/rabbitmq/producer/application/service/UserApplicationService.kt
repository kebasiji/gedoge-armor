package com.gedoge.armor.example.rabbitmq.producer.application.service

import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.User
import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource


@Service
class UserApplicationService {
    @Resource
    private lateinit var userRepository: UserRepository

    @Transactional
    fun addUser(name: String) {
        val user = User.create(name)
        userRepository.save(user)
        println("user $user")
        user.publishCreatedEvent()
    }

    fun getUser(userId: Long): User {
        return userRepository.findById(userId) ?: throw NullPointerException()
    }

    @Transactional
    fun updateUser(userId: Long, newName: String) {
        val user = userRepository.findById(userId) ?: throw NullPointerException()
        user.changeName(newName)
        user.publishUpdatedEvent()
    }

}