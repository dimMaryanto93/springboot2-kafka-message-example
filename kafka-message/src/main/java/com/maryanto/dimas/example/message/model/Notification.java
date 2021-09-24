package com.maryanto.dimas.example.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String transactionId;
    private String cardNumber;
    private String message;
}
