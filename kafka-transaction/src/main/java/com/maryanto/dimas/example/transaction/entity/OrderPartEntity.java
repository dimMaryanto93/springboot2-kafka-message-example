package com.maryanto.dimas.example.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_parts")
public class OrderPartEntity {

    public OrderPartEntity(String partNumber, Integer qty, String createdBy) {
        this.partNumber = partNumber;
        this.qty = qty;
        this.createdBy = createdBy;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
    }

    @Id
    @GenericGenerator(name = "uuid_gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid_gen")
    private String id;

    @Column(name = "part_number", length = 64, nullable = false)
    private String partNumber;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "created_time", nullable = false)
    private Timestamp createdDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy;
}
