package com.tabeldata.message.producer.example.service;

import com.tabeldata.message.model.Payment;
import com.tabeldata.message.topics.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaNoReplyService {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    public void send(Payment payment) {
        Message<Payment> message = MessageBuilder
                .withPayload(payment)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopics.PAYMENT_TOPIC)
                .build();
        this.template.send(message);
    }

    public void send(String desc) {
        Message<String> message = MessageBuilder
                .withPayload(desc)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopics.MESSAGE_TOPIC)
                .build();
        this.template.send(message);
    }

}
