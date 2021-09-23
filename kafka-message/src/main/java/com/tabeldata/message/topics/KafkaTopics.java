package com.tabeldata.message.topics;

public interface KafkaTopics {

    String PAYMENT_TOPIC = "payment";
    String MESSAGE_TOPIC = "message";
    String REPLY_TOPIC_REQUEST = "order-request";
    String REPLY_TOPIC_RESPONSE = "order-response";
}
