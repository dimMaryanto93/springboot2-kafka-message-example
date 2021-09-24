package com.maryanto.dimas.example.sendreply.controller;

import com.maryanto.dimas.example.sendreply.service.KafkaOrderReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/kafka")
public class KafkaOrderReplyController {

    @Autowired
    private KafkaOrderReplyService replyService;

    @PostMapping("/string/reply")
    public ResponseEntity<?> sendAndReply(@RequestParam String description) {
        try {
            String notif = this.replyService.sendAndReply(description);
            return ok(notif);
        } catch (ExecutionException | InterruptedException | TimeoutException ex) {
            return internalServerError().build();
        }
    }
}
