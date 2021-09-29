package com.maryanto.dimas.example.transaction.config;

import com.maryanto.dimas.example.transaction.model.KafkaModelContainer;
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
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
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
//        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "order_tx");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        DefaultKafkaProducerFactory<String, KafkaModelContainer> defaultPf = new DefaultKafkaProducerFactory<>(props);
        defaultPf.setTransactionIdPrefix("order_tx-");
        return defaultPf;
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
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
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
            MessageConverter converter,
            ChainedKafkaTransactionManager<String, KafkaModelContainer> trxManager) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaModelContainer> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        factory.setReplyTemplate(kafkaTemplate);
        factory.getContainerProperties().setTransactionManager(trxManager);
//        factory.setErrorHandler(new SeekToCurrentErrorHandler());
        return factory;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, KafkaModelContainer> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, KafkaModelContainer> containerFactory) {

        ConcurrentMessageListenerContainer<String, KafkaModelContainer> repliesContainer =
                containerFactory.createContainer("replies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
//        repliesContainer.setErrorHandler(new SeekToCurrentErrorHandler());
        return repliesContainer;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public KafkaTransactionManager<String, KafkaModelContainer> kafkaTransactionManager(
            ProducerFactory<String, KafkaModelContainer> pf) {
        return new KafkaTransactionManager<>(pf);
    }

    @Bean
    public ChainedKafkaTransactionManager<String, KafkaModelContainer> chainedKafkaTransactionManager(
            KafkaTransactionManager<String, KafkaModelContainer> kafkaTransactionManager,
            JpaTransactionManager transactionManager) {
        return new ChainedKafkaTransactionManager<>(kafkaTransactionManager, transactionManager);
    }
}
