package com.example.kafka_producer.config

import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig {
    private val logger = LoggerFactory.getLogger(KafkaTopicConfig::class.java)

    @Bean
    fun userEventsTopic(): NewTopic {
        logger.info("Configuring user-events Kafka topic")
        return TopicBuilder.name("user-events")
            .partitions(3)
            .replicas(1)
            .build().also {
                logger.info("User events topic configured successfully")
            }
    }
}