package com.tabeldata.message.producer.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private String transactionId;
    private String stan;
    private String cardNumber;
    private BigDecimal amount;

}
