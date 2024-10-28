package com.fawry.order_service.repository;

import com.fawry.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface OrderRepository extends JpaRepository<Orders,Long> {
}
