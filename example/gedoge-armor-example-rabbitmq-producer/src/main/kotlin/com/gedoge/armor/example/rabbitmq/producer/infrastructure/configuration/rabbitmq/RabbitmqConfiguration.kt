package com.gedoge.armor.example.rabbitmq.producer.infrastructure.configuration.rabbitmq

import com.gedoge.armor.example.rabbitmq.producer.infrastructure.configuration.constants.EXCHANGE_ARMOR_EXAMPLE_USER
import com.gedoge.armor.example.rabbitmq.producer.infrastructure.configuration.constants.QUEUE_ARMOR_EXAMPLE_USER_CREATED
import com.gedoge.armor.example.rabbitmq.producer.infrastructure.configuration.constants.QUEUE_ARMOR_EXAMPLE_USER_UPDATED
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct
import javax.annotation.Resource

@Configuration
class RabbitmqConfiguration {
    @Resource
    private lateinit var amqpAdmin: AmqpAdmin

    @Resource
    private lateinit var rabbitTemplate: RabbitTemplate

    @Resource
    private lateinit var rabbitmqReturnCallback: RabbitTemplate.ReturnsCallback

    @PostConstruct
    fun postConstruct() {
        rabbitTemplate.setReturnsCallback(rabbitmqReturnCallback)

        amqpAdmin.declareExchange(TopicExchange(EXCHANGE_ARMOR_EXAMPLE_USER, true, false))
        amqpAdmin.declareQueue(Queue(QUEUE_ARMOR_EXAMPLE_USER_CREATED, true, false, false))
        amqpAdmin.declareQueue(Queue(QUEUE_ARMOR_EXAMPLE_USER_UPDATED, true, false, false))
        amqpAdmin.declareBinding(
            Binding(
                QUEUE_ARMOR_EXAMPLE_USER_CREATED,
                Binding.DestinationType.QUEUE,
                EXCHANGE_ARMOR_EXAMPLE_USER,
                "created",
                null
            )
        )
        amqpAdmin.declareBinding(
            Binding(
                QUEUE_ARMOR_EXAMPLE_USER_UPDATED,
                Binding.DestinationType.QUEUE,
                EXCHANGE_ARMOR_EXAMPLE_USER,
                "updated",
                null
            )
        )
    }

}