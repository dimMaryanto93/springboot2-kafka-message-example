package com.tabeldata.message.sendreplyjson.example.controller;

import com.tabeldata.message.model.Payment;
import com.tabeldata.message.sendreplyjson.example.model.KafkaModelContainer;
import com.tabeldata.message.sendreplyjson.example.service.KafkaJsonReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/kafka")
public class KafkaJsonReplyController {

    @Autowired
    private KafkaJsonReplyService replyService;

    @PostMapping("/json/reply")
    public ResponseEntity<?> sendAndReply(@RequestBody Payment payment) {
        KafkaModelContainer request = new KafkaModelContainer();
        request.setPayload(payment);
        try {
            KafkaModelContainer respose = this.replyService.sendAndReply(request);
            return ok(respose.getPayload());
        } catch (ExecutionException | InterruptedException | TimeoutException ex) {
            return internalServerError().build();
        }
    }
}
