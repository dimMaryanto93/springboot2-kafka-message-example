package com.tabeldata.message.producer.example.service;

import com.tabeldata.message.producer.example.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    public void send(Payment payment) {
        this.template.send(
                new GenericMessage<>(payment,
                        Collections.singletonMap(KafkaHeaders.TOPIC, "payment")
                )
        );
    }


}
