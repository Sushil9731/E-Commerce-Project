package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
