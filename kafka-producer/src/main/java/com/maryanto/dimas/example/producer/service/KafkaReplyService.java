package com.maryanto.dimas.example.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
