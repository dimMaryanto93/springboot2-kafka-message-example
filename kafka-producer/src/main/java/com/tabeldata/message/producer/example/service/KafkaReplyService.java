package com.tabeldata.message.producer.example.service;

import com.tabeldata.message.model.Notification;
import com.tabeldata.message.model.Payment;
import com.tabeldata.message.producer.example.config.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyMessageFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class KafkaReplyService {

//    @Autowired
//    private ReplyingKafkaTemplate<String, Payment, Notification> template;
//
//    public Notification send(Payment payment) throws ExecutionException, InterruptedException, TimeoutException {
//        Message<Payment> message = MessageBuilder
//                .withPayload(payment)
//                .setHeader(KafkaHeaders.TOPIC, KafkaTopics.REPLY_TOPIC_REQUEST)
//                .build();
//        RequestReplyMessageFuture<String, Payment> replyFuture =
//                this.template.sendAndReceive(message, Duration.ofSeconds(5));
//        SendResult<String, Payment> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
//        log.info("send ok: {}", sendResult.getRecordMetadata());
//
//        Message<Notification> replyResult = (Message<Notification>) replyFuture.get(10, TimeUnit.SECONDS);
//        return replyResult.getPayload();
//    }
}
