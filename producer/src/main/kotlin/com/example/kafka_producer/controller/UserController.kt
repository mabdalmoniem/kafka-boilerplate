package com.example.kafka_producer.controller


import com.example.kafka_producer.dto.UserMessage
import com.example.kafka_producer.service.UserEventProducer
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(
    private val userEventProducer: UserEventProducer
) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/create")
    fun createUser(@RequestBody userMessage: UserMessage): String {
        val userId = UUID.randomUUID().toString()

        logger.info("Attempting to create user message with username: {}", userMessage.name)

        userEventProducer.sendUserEvent(userId, userMessage, "USER_MESSAGE_CREATED")

        logger.info("User message was created successfully with ID: {}", userId)

        return "User message was created with ID: $userId"
    }
}