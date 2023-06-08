package com.gedoge.armor.example.rabbitmq.producer.api.http.user

import com.gedoge.armor.example.rabbitmq.producer.application.service.UserApplicationService
import com.gedoge.armor.example.rabbitmq.producer.domain.model.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.annotation.Resource

@RestController
@RequestMapping("/users")
class UserController {
    @Resource
    private lateinit var userApplicationService: UserApplicationService

    @PostMapping
    fun addUser() {
        userApplicationService.addUser(UUID.randomUUID().toString())
    }

    @PutMapping("{userId}")
    fun updateUser(@PathVariable("userId") userId: Long) {
        userApplicationService.updateUser(userId, UUID.randomUUID().toString())
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: Long): User {
        return userApplicationService.getUser(userId)
    }

}