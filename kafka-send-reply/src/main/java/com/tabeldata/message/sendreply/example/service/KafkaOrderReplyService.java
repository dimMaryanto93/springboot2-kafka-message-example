package com.tabeldata.message.sendreply.example.service;

import com.tabeldata.message.topics.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class KafkaOrderReplyService {

    @Autowired
    ReplyingKafkaTemplate<String, String, String> template;

    public String sendAndReply(String param) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaTopics.REPLY_TOPIC_REQUEST, param);
        RequestReplyFuture<String, String, String> replyFuture = template.sendAndReceive(record);
        SendResult<String, String> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        log.info("Send ok: {}", sendResult.getRecordMetadata());

        ConsumerRecord<String, String> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        log.info("Return value: {}", consumerRecord.value());
        return consumerRecord.value();
    }
}
