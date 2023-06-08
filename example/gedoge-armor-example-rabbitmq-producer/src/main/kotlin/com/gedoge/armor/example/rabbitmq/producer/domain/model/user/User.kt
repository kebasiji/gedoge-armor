package com.gedoge.armor.example.rabbitmq.producer.domain.model.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gedoge.armor.core.domain.DomainEventPublisher
import com.gedoge.armor.example.rabbitmq.producer.utils.SingletonContext
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "user")
@Entity
class User(
    name: String
) {

    @GenericGenerator(
        name = "entity_id",
        strategy = "com.gedoge.armor.example.rabbitmq.producer.infrastructure.persistence.repository.EntityIdGenerator"
    )
    @GeneratedValue(generator = "entity_id")
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long? = null
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @field:CreationTimestamp
    @field:Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null
        protected set

    @field:UpdateTimestamp
    @field:Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
        protected set

    fun changeName(newName: String) {
        this.name = newName
    }

    @get:JsonIgnore
    val domainEventPublisher: DomainEventPublisher
        get() = SingletonContext.getSingleton()

    fun publishCreatedEvent() {
        val event = UserCreatedEvent(this)
        domainEventPublisher.publish(event)
    }

    fun publishUpdatedEvent() {
        val event = UserUpdatedEvent(this)
        domainEventPublisher.publish(event)
    }

    override fun toString(): String {
        return "User(id=$id, name='$name', createdAt=$createdAt, updatedAt=$updatedAt)"
    }

    companion object {

        fun create(name: String): User {
            return User(name = name)
        }

    }

}