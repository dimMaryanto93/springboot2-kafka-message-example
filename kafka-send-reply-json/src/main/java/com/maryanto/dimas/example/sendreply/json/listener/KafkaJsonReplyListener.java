package com.maryanto.dimas.example.sendreply.json.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maryanto.dimas.example.sendreply.json.model.KafkaModelContainer;
import com.maryanto.dimas.example.message.model.Notification;
import com.maryanto.dimas.example.message.model.Payment;
import com.maryanto.dimas.example.message.topics.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaJsonReplyListener {

    private ObjectMapper objectMapper;

    @KafkaListener(
            id = "server",
            topics = KafkaTopics.REPLY_JSON_TOPIC_REQUEST)
    @SendTo // use default replyTo expression
    public KafkaModelContainer listen(KafkaModelContainer in) throws JsonProcessingException {
        log.info("message received from server: {}", in);
        this.objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(in.getPayload());
        Payment payment = objectMapper.readValue(json, Payment.class);

        Notification notif = new Notification(
                payment.getTransactionId(),
                payment.getCardNumber(),
                "Selamat pembayaran anda sebesar " + payment.getAmount() + " telah berhasil");
        return new KafkaModelContainer(notif);
    }
}
