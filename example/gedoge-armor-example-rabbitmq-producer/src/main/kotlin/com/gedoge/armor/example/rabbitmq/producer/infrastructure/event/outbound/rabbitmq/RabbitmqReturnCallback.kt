package com.gedoge.armor.example.rabbitmq.producer.infrastructure.event.outbound.rabbitmq

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.ReturnedMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitmqReturnCallback : RabbitTemplate.ReturnsCallback {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun returnedMessage(returned: ReturnedMessage) {
        logger.error("get returned message {}", returned)
    }

}
