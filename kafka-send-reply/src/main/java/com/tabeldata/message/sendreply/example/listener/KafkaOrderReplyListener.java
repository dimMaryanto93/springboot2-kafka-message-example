package com.tabeldata.message.sendreply.example.listener;

import com.tabeldata.message.topics.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaOrderReplyListener {

    @KafkaListener(id = "server", topics = KafkaTopics.REPLY_TOPIC_REQUEST)
    @SendTo // use default replyTo expression
    public String listen(String in) {
        log.info("message received from server: {}", in);
        return in.toUpperCase();
    }
}
