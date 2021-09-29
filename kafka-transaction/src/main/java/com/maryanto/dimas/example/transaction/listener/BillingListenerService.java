package com.maryanto.dimas.example.transaction.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maryanto.dimas.example.message.model.OrderPart;
import com.maryanto.dimas.example.message.topics.KafkaTopics;
import com.maryanto.dimas.example.transaction.model.KafkaModelContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(id = "trx-billing-service",
        topics = KafkaTopics.TRANSACTION_CREATE_BILL_TOPIC)
public class BillingListenerService {

    private ObjectMapper objectMapper;

    @KafkaHandler
    public void listen(KafkaModelContainer in) throws JsonProcessingException {
        this.objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(in.getPayload());
        OrderPart part = objectMapper.readValue(json, OrderPart.class);
        log.info("from billing: {}", part);

    }
}
