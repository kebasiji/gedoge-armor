package com.gedoge.armor.example.rabbitmq.consumer.infrastructure.configuration.rabbitmq

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct
import javax.annotation.Resource

@Configuration
class RabbitmqConfiguration {
    @Resource
    private lateinit var amqpAdmin: AmqpAdmin

    @PostConstruct
    fun postConstruct() {
        amqpAdmin.declareExchange(TopicExchange("exchange.armor.example.user", true, false))
        amqpAdmin.declareQueue(Queue("queue.armor.example.user.created", true, false, false))
        amqpAdmin.declareQueue(Queue("queue.armor.example.user.updated", true, false, false))
        amqpAdmin.declareBinding(
            Binding(
                "queue.armor.example.user.created",
                Binding.DestinationType.QUEUE,
                "exchange.armor.example.user",
                "created",
                null
            )
        )
        amqpAdmin.declareBinding(
            Binding(
                "queue.armor.example.user.updated",
                Binding.DestinationType.QUEUE,
                "exchange.armor.example.user",
                "updated",
                null
            )
        )
    }

}