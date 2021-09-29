package com.maryanto.dimas.example.transaction.config;

import com.maryanto.dimas.example.message.topics.KafkaTopics;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.SimpleKafkaHeaderMapper;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Bean // not required if Jackson is on the classpath
    public MessagingMessageConverter simpleMapperConverter() {
        MessagingMessageConverter messagingMessageConverter = new MessagingMessageConverter();
        messagingMessageConverter.setHeaderMapper(new SimpleKafkaHeaderMapper());
        return messagingMessageConverter;
    }

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic transactionCreateBillTopic() {
        return TopicBuilder.name(KafkaTopics.TRANSACTION_CREATE_BILL_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic transactionCreateVaTopic() {
        return TopicBuilder.name(KafkaTopics.TRANSACTION_CREATE_VA_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }

}
