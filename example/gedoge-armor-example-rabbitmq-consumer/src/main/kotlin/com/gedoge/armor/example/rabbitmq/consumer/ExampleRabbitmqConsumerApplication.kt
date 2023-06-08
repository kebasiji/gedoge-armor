package com.gedoge.armor.example.rabbitmq.consumer

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.gedoge.armor.example.rabbitmq.consumer.utils.SingletonContext
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootApplication
class ExampleRabbitmqConsumerApplication {

    @Bean
    fun objectMapper(): ObjectMapper {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTimeDeserializer = LocalDateTimeDeserializer(dateTimeFormatter)
        val localDateTimeSerializer = LocalDateTimeSerializer(dateTimeFormatter)
        return jacksonObjectMapper()
            .enable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
            .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(ParameterNamesModule())
            .registerModule(Jdk8Module())
            .registerModule(
                JavaTimeModule().addDeserializer(LocalDateTime::class.java, localDateTimeDeserializer)
                    .addSerializer(LocalDateTime::class.java, localDateTimeSerializer)
            )
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

}

fun main(args: Array<String>) {
    SingletonContext.applicationContext = SpringApplication.run(ExampleRabbitmqConsumerApplication::class.java, *args)
}
