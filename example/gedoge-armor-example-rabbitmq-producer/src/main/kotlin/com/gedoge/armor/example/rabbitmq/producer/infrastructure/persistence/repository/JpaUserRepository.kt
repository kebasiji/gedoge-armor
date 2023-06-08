package com.gedoge.armor.example.rabbitmq.producer.infrastructure.persistence.repository

import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.User
import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.UserRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Transactional
@Repository
class JpaUserRepository : UserRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun save(user: User) {
        em.persist(user)
    }

    override fun findById(id: Long): User? {
        return em.find(User::class.java, id)
    }

}