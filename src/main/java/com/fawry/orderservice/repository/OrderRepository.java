package com.fawry.orderservice.repository;

import com.fawry.orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface OrderRepository extends JpaRepository<Orders,Long> {
}
