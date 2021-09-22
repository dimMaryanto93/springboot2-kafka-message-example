package com.tabeldata.message.producer.example.controller;

import com.tabeldata.message.model.Payment;
import com.tabeldata.message.producer.example.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping(path = "/json/no-reply")
    public void sendJsonFormat(@RequestBody Payment payment) {
        this.service.send(payment);
    }

    @PostMapping(path = "/string/no-reply")
    public void sendStringFormat(@RequestParam String desc) {
        this.service.send(desc);
    }

}
