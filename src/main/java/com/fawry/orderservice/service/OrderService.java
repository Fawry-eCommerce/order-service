package com.fawry.orderservice.service;

import com.fawry.orderservice.entity.Orders;
import com.fawry.orderservice.model.OrderModel;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    void createOrder(OrderModel orderModel);

}
