package com.tabeldata.message.producer.example.service;

import com.tabeldata.message.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    public void send(Payment payment) {
        Message<Payment> message = MessageBuilder
                .withPayload(payment)
                .setHeader(KafkaHeaders.TOPIC, "payment")
                .build();
        this.template.send(message);
    }


}
