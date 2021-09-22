package com.tabeldata.message.producer.example.controller;

import com.tabeldata.message.model.Notification;
import com.tabeldata.message.model.Payment;
import com.tabeldata.message.producer.example.service.KafkaNoReplyService;
import com.tabeldata.message.producer.example.service.KafkaReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaNoReplyService service;

    @Autowired
    private KafkaReplyService replyService;

    @PostMapping(path = "/json/no-reply")
    public void sendJsonFormat(@RequestBody Payment payment) {
        this.service.send(payment);
    }

    @PostMapping(path = "/string/no-reply")
    public void sendStringFormat(@RequestParam String desc) {
        this.service.send(desc);
    }

//    @PostMapping("/json/reply")
//    public ResponseEntity<?> sendAndReply(@RequestBody Payment payment) {
//        try {
//            Notification notif = this.replyService.send(payment);
//            return ok(notif);
//        } catch (ExecutionException | InterruptedException | TimeoutException ex) {
//            return internalServerError().build();
//        }
//    }

}
