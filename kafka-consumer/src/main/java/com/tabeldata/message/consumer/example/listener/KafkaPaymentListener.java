package com.tabeldata.message.consumer.example.listener;

import com.tabeldata.message.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(
        id = "payment-gateway-listener",
        topics = {"payment"}
)
public class KafkaPaymentListener {

    @KafkaHandler
    public void getPaymentMethod(
            @Header(KafkaHeaders.GROUP_ID) String groupId,
            @Header(name = KafkaHeaders.PARTITION_ID, required = false) Integer partitionId,
            @Payload Payment payment) {
        log.info("Payment {payload: {}, groupId: {}, partitionId: {}}",
                payment, groupId, partitionId);
    }

}
