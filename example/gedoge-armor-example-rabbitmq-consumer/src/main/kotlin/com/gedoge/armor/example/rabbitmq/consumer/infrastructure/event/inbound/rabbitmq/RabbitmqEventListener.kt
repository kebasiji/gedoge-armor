package com.gedoge.armor.example.rabbitmq.consumer.infrastructure.event.inbound.rabbitmq

import com.gedoge.armor.core.inbound.InboundEventProcessor
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate

@Component
class RabbitmqEventListener(
    private val transactionalTemplate: TransactionTemplate,
    private val persistenceAndRepublishProcessor: InboundEventProcessor
) {

    @RabbitListener(queues = ["queue.armor.example.user.created", "queue.armor.example.user.updated"])
    fun onEvent(eventJsonString: String) {
        transactionalTemplate.execute {
            persistenceAndRepublishProcessor.process(eventJsonString)
        }
    }

}