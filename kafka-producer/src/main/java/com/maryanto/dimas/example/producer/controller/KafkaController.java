package com.maryanto.dimas.example.producer.controller;

import com.maryanto.dimas.example.message.model.Payment;
import com.maryanto.dimas.example.producer.service.KafkaNoReplyService;
import com.maryanto.dimas.example.producer.service.KafkaReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
