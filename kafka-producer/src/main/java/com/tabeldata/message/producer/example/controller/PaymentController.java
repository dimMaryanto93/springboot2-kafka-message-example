package com.tabeldata.message.producer.example.controller;

import com.tabeldata.message.producer.example.model.Payment;
import com.tabeldata.message.producer.example.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping(path = "/no-reply")
    public void send(@RequestBody Payment payment) {
        this.service.send(payment);
    }

}
