package com.maryanto.dimas.example.message.topics;

public interface KafkaTopics {

    String PAYMENT_TOPIC = "payment";
    String MESSAGE_TOPIC = "message";
    String REPLY_TOPIC_REQUEST = "order-request";
    String REPLY_JSON_TOPIC_REQUEST = "order-json-request";
}
