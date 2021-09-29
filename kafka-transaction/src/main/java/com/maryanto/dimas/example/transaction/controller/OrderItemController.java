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
    public ResponseEntity<?> withinTransaction(@RequestBody OrderPart order) {
        /**
         * #FIXME may problem in kafka config
         * errors: kafka_1      | org.apache.kafka.common.errors.InvalidReplicationFactorExcept
         * ion: Replication factor: 3 larger than available brokers: 1.
         */
        this.orderService.withinTransaction(order);

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/without-transaction/success")
    public ResponseEntity<?> withoutTransaction(@RequestBody OrderPart order) {
        this.orderService.withoutTransaction(order);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/proxy-transaction/success")
    public ResponseEntity<?> proxyTransaction(@RequestBody OrderPart order) {
        /**
         * #FIXME may problem in kafka config
         * errors: kafka_1      | org.apache.kafka.common.errors.InvalidReplicationFactorExcept
         * ion: Replication factor: 3 larger than available brokers: 1.
         */

        this.orderService.proxyTransaction(order);
        return ResponseEntity.ok()
                .build();
    }
}
