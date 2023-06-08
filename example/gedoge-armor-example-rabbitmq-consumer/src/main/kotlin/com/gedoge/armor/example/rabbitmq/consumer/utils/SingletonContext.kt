package com.gedoge.armor.example.rabbitmq.consumer.utils

import org.springframework.context.ApplicationContext

object SingletonContext {
    lateinit var applicationContext: ApplicationContext

    @Suppress("UNCHECKED_CAST")
    fun <T> getSingleton(name: String): T {
        return applicationContext.getBean(name) as T
    }

    inline fun <reified T> getSingleton(): T {
        return applicationContext.getBean(T::class.java)
    }

}