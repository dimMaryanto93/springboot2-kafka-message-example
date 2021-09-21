package com.tabeldata.message.listener.example.service.listener;


import com.tabeldata.message.listener.example.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(
        id = "payment-gateway-listener",
        topics = {"payment"})
public class KafkaPaymentListener {

    @KafkaHandler
    public void getPaymentMethod(@Payload Payment payment) {
        log.info("Received payment: {}", payment);
    }

    @KafkaHandler(isDefault = true)
    public void getMessage(@Payload String message) {
        log.info("Received unknown: {}", message);
    }

}
