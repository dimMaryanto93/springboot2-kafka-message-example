package com.maryanto.dimas.example.transaction.controller;

import com.maryanto.dimas.example.message.model.OrderPart;
import com.maryanto.dimas.example.transaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class OrderItemController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/transaction/success")
    public ResponseEntity<?> sendAndReply(@RequestBody OrderPart order) {
        this.orderService.orderItems(order);

        return ResponseEntity.ok()
                .build();
    }
}
