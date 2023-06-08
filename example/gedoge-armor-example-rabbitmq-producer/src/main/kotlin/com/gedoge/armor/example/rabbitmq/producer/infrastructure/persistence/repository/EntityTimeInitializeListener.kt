package com.gedoge.armor.example.rabbitmq.producer.infrastructure.persistence.repository

import java.time.LocalDateTime
import javax.persistence.PrePersist
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class EntityTimeInitializeListener {

    @PrePersist
    fun setCreationTime(entity: Any) {
        val now = LocalDateTime.now()
        val createdAt = entity::class.memberProperties.find { it.name == "createdAt" }
        if (createdAt is KMutableProperty<*>) {
            createdAt.isAccessible = true
            createdAt.setter.call(entity, now)
        }
        val updatedAt = entity::class.memberProperties.find { it.name == "updatedAt" }
        if (updatedAt is KMutableProperty<*>) {
            updatedAt.isAccessible = true
            updatedAt.setter.call(entity, now)
        }
    }

}