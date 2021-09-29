package com.maryanto.dimas.example.transaction.repository;

import com.maryanto.dimas.example.transaction.entity.OrderPartEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderPartEntity, String> {
}
