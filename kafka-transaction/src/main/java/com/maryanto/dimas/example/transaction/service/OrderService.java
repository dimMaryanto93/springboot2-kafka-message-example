package com.maryanto.dimas.example.transaction.service;

import com.maryanto.dimas.example.message.model.OrderPart;
import com.maryanto.dimas.example.message.topics.KafkaTopics;
import com.maryanto.dimas.example.transaction.entity.OrderPartEntity;
import com.maryanto.dimas.example.transaction.model.KafkaModelContainer;
import com.maryanto.dimas.example.transaction.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private KafkaOperations<String, KafkaModelContainer> kafkaTemplate;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public void withinTransaction(OrderPart order) {
        KafkaModelContainer request = new KafkaModelContainer(order);
        Message<KafkaModelContainer> createBillMessage =
                MessageBuilder
                        .withPayload(request)
                        .setHeader(KafkaHeaders.TOPIC, KafkaTopics.TRANSACTION_CREATE_BILL_TOPIC)
                        .build();

        Message<KafkaModelContainer> createVirtualAccountMessage =
                MessageBuilder.withPayload(request)
                        .setHeader(KafkaHeaders.TOPIC, KafkaTopics.TRANSACTION_CREATE_VA_TOPIC)
                        .build();

//        uncomment this for using local transaction
        this.kafkaTemplate.executeInTransaction(event -> {
            event.inTransaction();
            event.send(createBillMessage);
            event.send(createVirtualAccountMessage);

            this.orderItemRepository.save(new OrderPartEntity(
                    order.getPartNumber(),
                    order.getQty(),
                    order.getRequestBy()));
            return true;
        });

    }

    public void withoutTransaction(OrderPart order) {
        KafkaModelContainer request = new KafkaModelContainer(order);
        Message<KafkaModelContainer> createBillMessage =
                MessageBuilder
                        .withPayload(request)
                        .setHeader(KafkaHeaders.TOPIC, KafkaTopics.TRANSACTION_CREATE_BILL_TOPIC)
                        .build();

        Message<KafkaModelContainer> createVirtualAccountMessage =
                MessageBuilder.withPayload(request)
                        .setHeader(KafkaHeaders.TOPIC, KafkaTopics.TRANSACTION_CREATE_VA_TOPIC)
                        .build();

        kafkaTemplate.send(createBillMessage);
        kafkaTemplate.send(createVirtualAccountMessage);
    }

    @Transactional
    public void proxyTransaction(OrderPart order) {
        KafkaModelContainer request = new KafkaModelContainer(order);
        Message<KafkaModelContainer> createBillMessage =
                MessageBuilder
                        .withPayload(request)
                        .setHeader(KafkaHeaders.TOPIC, KafkaTopics.TRANSACTION_CREATE_BILL_TOPIC)
                        .build();

        Message<KafkaModelContainer> createVirtualAccountMessage =
                MessageBuilder.withPayload(request)
                        .setHeader(KafkaHeaders.TOPIC, KafkaTopics.TRANSACTION_CREATE_VA_TOPIC)
                        .build();

        kafkaTemplate.send(createBillMessage);
        kafkaTemplate.send(createVirtualAccountMessage);
        kafkaTemplate.flush();

        this.orderItemRepository.save(new OrderPartEntity(
                order.getPartNumber(),
                order.getQty(),
                order.getRequestBy()));

    }
}
