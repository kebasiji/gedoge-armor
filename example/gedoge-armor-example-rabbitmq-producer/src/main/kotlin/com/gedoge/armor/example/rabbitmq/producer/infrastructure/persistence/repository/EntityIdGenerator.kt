package com.gedoge.armor.example.rabbitmq.producer.infrastructure.persistence.repository

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable

class EntityIdGenerator : IdentifierGenerator {

    override fun generate(session: SharedSessionContractImplementor, `object`: Any): Serializable {
        return System.currentTimeMillis()
    }

}