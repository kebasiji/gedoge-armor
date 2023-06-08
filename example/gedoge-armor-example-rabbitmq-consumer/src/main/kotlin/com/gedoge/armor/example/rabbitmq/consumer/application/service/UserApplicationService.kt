package com.gedoge.armor.example.rabbitmq.consumer.application.service

import com.gedoge.armor.example.rabbitmq.consumer.domain.model.user.UserCreatedEvent
import com.gedoge.armor.example.rabbitmq.consumer.domain.model.user.UserUpdatedEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service


@Service
class UserApplicationService {
    private val logger = LoggerFactory.getLogger(UserApplicationService::class.java)

    //    @Transactional
    @EventListener
    fun onUserCreated(event: UserCreatedEvent) {
        logger.info("onUserCreated {}", event)
    }

    //    @Transactional
    @EventListener
    fun onUserUpdated(event: UserUpdatedEvent) {
        logger.info("onUserUpdated {}", event)
    }

}