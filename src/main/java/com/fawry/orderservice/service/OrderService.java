package com.fawry.order_service.service;

import com.fawry.order_service.entity.Orders;
import com.fawry.order_service.model.OrderModel;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    void createOrder(OrderModel orderModel);

}
