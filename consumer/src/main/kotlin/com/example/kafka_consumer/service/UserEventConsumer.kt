package com.example.kafka_consumer.service

import com.beust.klaxon.Klaxon
import com.example.kafka_consumer.dto.UserMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class UserEventConsumer{
    private val logger = LoggerFactory.getLogger(UserEventConsumer::class.java)

    @KafkaListener(
        topics = ["user-events"],
        groupId = "user-events-consumer-group"
    )
    fun consumeUserEvent(message: String) {
        logger.info("Received user event: {}", message)

        // Optional: Add more detailed processing logic
        try {
            // Example: Parse or process the message
            processUserEvent(message)
        } catch (e: Exception) {
            logger.error("Error processing user event", e)
        }
    }

    private fun processUserEvent(message: String) {
        logger.info("Received raw user event: {}", message)

        try {
            val userMessage = Klaxon().parse<UserMessage>(message)
            logger.info("Parsed message - Name: {}, Content: {}", userMessage?.name, userMessage?.message)
        } catch (e: Exception) {
            logger.error("Error parsing message", e)
        }
    }
}