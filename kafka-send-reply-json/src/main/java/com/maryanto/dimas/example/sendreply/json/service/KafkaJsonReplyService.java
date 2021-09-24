package com.maryanto.dimas.example.sendreply.json.service;

import com.maryanto.dimas.example.sendreply.json.model.KafkaModelContainer;
import com.maryanto.dimas.example.message.topics.KafkaTopics;
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
public class KafkaJsonReplyService {

    @Autowired
    ReplyingKafkaTemplate<String, KafkaModelContainer, KafkaModelContainer> template;

    public KafkaModelContainer sendAndReply(KafkaModelContainer param) throws ExecutionException, InterruptedException, TimeoutException {

        ProducerRecord<String, KafkaModelContainer> record =
                new ProducerRecord<>(KafkaTopics.REPLY_JSON_TOPIC_REQUEST, param);
        RequestReplyFuture<String, KafkaModelContainer, KafkaModelContainer> replyFuture =
                template.sendAndReceive(record);

        SendResult<String, KafkaModelContainer> sendResult =
                replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        log.info("Send ok: {}", sendResult.getRecordMetadata());

        ConsumerRecord<String, KafkaModelContainer> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        log.info("Return value: {}", consumerRecord.value());
        return consumerRecord.value();
    }
}
