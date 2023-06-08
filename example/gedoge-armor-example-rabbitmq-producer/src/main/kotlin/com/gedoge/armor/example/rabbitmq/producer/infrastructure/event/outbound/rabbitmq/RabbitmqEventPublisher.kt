package com.gedoge.armor.example.rabbitmq.producer.infrastructure.event.outbound.rabbitmq

import com.gedoge.armor.core.impl.outbound.CombinedPublishListener
import com.gedoge.armor.core.outbound.OutboundEvent
import com.gedoge.armor.core.spi.EventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitmqEventPublisher(
    private val combinedPublishListener: CombinedPublishListener,
    private val rabbitmqTemplate: RabbitTemplate,
) : EventPublisher {

    private val logger = LoggerFactory.getLogger(RabbitmqEventPublisher::class.java)

    override fun publish(event: OutboundEvent) {
        logger.info("sending event {}", event.toJsonString())
        val correlationData = CorrelationData()
        val message = MessageBuilder.withBody(event.toJsonString().toByteArray(Charsets.UTF_8))
            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
            .setMessageId(event.eventId)
            .build()
        rabbitmqTemplate.send(event.destination, event.tag ?: "", message, correlationData)
        correlationData.future.addCallback({ confirm ->
            if (confirm?.isAck == true) {
                combinedPublishListener.onSent(event)
            } else {
                logger.error("send event failed {}", event.toJsonString())
            }
        }) { throwable ->
            logger.error("send event failed", throwable)
        }
    }

}
