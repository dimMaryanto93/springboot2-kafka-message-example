package com.maryanto.dimas.example.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPart {

    private String partNumber;
    private Integer qty;
    private String requestBy;
}
