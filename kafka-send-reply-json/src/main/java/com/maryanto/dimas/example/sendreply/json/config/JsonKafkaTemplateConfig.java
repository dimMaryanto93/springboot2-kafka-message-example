package com.maryanto.dimas.example.sendreply.json.config;

import com.maryanto.dimas.example.sendreply.json.model.KafkaModelContainer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.ParseStringDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JsonKafkaTemplateConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackage;

    @Bean
    public ProducerFactory<String, KafkaModelContainer> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, KafkaModelContainer> kafkaTemplate(
            ProducerFactory<String, KafkaModelContainer> pf,
            RecordMessageConverter converter) {
        KafkaTemplate<String, KafkaModelContainer> template = new KafkaTemplate<>(pf);
//        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public ConsumerFactory<String, KafkaModelContainer> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ParseStringDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, this.trustedPackage);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, false);


        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaModelContainer.class));
    }


    @Bean
    public ReplyingKafkaTemplate<String, KafkaModelContainer, KafkaModelContainer> replyingTemplate(
            ProducerFactory<String, KafkaModelContainer> pf,
            ConcurrentMessageListenerContainer<String, KafkaModelContainer> repliesContainer,
            RecordMessageConverter converter) {
        ReplyingKafkaTemplate<String, KafkaModelContainer, KafkaModelContainer> template =
                new ReplyingKafkaTemplate<>(pf, repliesContainer);
//        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaModelContainer> kafkaListenerContainerFactory(
            KafkaTemplate<String, KafkaModelContainer> kafkaTemplate,
            ConsumerFactory<String, KafkaModelContainer> cf,
            MessageConverter converter) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaModelContainer> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, KafkaModelContainer> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, KafkaModelContainer> containerFactory) {

        ConcurrentMessageListenerContainer<String, KafkaModelContainer> repliesContainer =
                containerFactory.createContainer("replies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }
}
