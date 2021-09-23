package com.tabeldata.message.reply.example.listener;

import com.tabeldata.message.model.Notification;
import com.tabeldata.message.model.Payment;
import com.tabeldata.message.topics.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(
        id = "payment-gateway-reply-listener",
        topics = {KafkaTopics.REPLY_TOPIC_REQUEST}
)
public class KafkaPaymentReplyListener {

    @SendTo
    @KafkaHandler
    public Notification getPaymentMethod(
            @Header(KafkaHeaders.GROUP_ID) String groupId,
            @Header(name = KafkaHeaders.PARTITION_ID, required = false) Integer partitionId,
            @Payload Payment payment) {
        log.info("Payment {payload: {}, groupId: {}, partitionId: {}}",
                payment, groupId, partitionId);
        Notification notification = new Notification(payment.getTransactionId(),
                payment.getCardNumber(),
                new StringBuilder("Pembayaran anda sebesar ")
                        .append(payment.getAmount())
                        .append(" Telah berhasil").toString()
        );
        return notification;
    }
}
