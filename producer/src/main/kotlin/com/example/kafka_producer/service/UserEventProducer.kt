package com.example.kafka_producer.service

import com.example.kafka_producer.dto.UserMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder

@Service
class UserEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val logger = LoggerFactory.getLogger(UserEventProducer::class.java)

    fun sendUserEvent(userId: String, userMessage: UserMessage, eventType: String) {
        val message = "User event: $eventType for user $userId ${userMessage.name}, message: ${userMessage.message}"

        try {
            // Build a message with headers for more context
            val kafkaMessage = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, "user-events")
                .setHeader(KafkaHeaders.KEY, userId)
                .build()

            // Send with a specific timeout
            val future: CompletableFuture<SendResult<String, String>> = kafkaTemplate.send(kafkaMessage)

            try {
                // Wait for the result with a timeout
                val result = future.get(5, TimeUnit.SECONDS)
                logger.info("Sent message=[{}] with offset=[{}]", message, result.recordMetadata.offset())
            } catch (e: Exception) {
                // Comprehensive error logging
                logger.error("Failed to send Kafka message", e)
                when (e) {
                    is java.util.concurrent.TimeoutException ->
                        logger.error("Kafka message send timed out for userId: {}", userId)
                    is InterruptedException ->
                        logger.error("Kafka message send was interrupted for userId: {}", userId)
                    else ->
                        logger.error("Unexpected error sending Kafka message for userId: {}", userId, e)
                }

                // Rethrow to allow global error handling
                throw RuntimeException("Failed to send Kafka message", e)
            }
        } catch (e: Exception) {
            // Catch any exception during message preparation
            logger.error("Error preparing Kafka message", e)
            throw RuntimeException("Error preparing Kafka message", e)
        }
    }
}