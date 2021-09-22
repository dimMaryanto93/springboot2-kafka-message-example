package com.tabeldata.message.producer.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name(KafkaTopics.PAYMENT_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopicRequest() {
        return TopicBuilder.name(KafkaTopics.REPLY_TOPIC_REQUEST)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopicResponse() {
        return TopicBuilder.name(KafkaTopics.REPLY_TOPIC_RESPONSE)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(KafkaTopics.MESSAGE_TOPIC)
                .partitions(10)
                .replicas(1)
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                .build();
    }

}
