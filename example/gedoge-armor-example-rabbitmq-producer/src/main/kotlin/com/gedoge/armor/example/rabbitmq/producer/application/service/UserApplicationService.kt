package com.gedoge.armor.example.rabbitmq.producer.application.service

import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.User
import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource


@Service
class UserApplicationService {
    private val logger = LoggerFactory.getLogger(UserApplicationService::class.java)

    @Resource
    private lateinit var userRepository: UserRepository

    @Transactional
    fun addUser(name: String) {
        val user = User.create(name)
        userRepository.save(user)
        logger.info("add user {}", user)
        user.publishCreatedEvent()
    }

    fun getUser(userId: Long): User {
        return userRepository.findById(userId) ?: throw NullPointerException()
    }

    @Transactional
    fun updateUser(userId: Long, newName: String) {
        val user = userRepository.findById(userId) ?: throw NullPointerException()
        user.changeName(newName)
        logger.info("update user {}", user)
        user.publishUpdatedEvent()
    }

}