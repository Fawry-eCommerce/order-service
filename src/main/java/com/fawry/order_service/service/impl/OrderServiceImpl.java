package com.fawry.order_service.service.impl;

import com.fawry.order_service.entity.Orders;
import com.fawry.order_service.mapper.OrderMapper;
import com.fawry.order_service.model.OrderModel;
import com.fawry.order_service.repository.OrderRepository;
import com.fawry.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public void createOrder(OrderModel orderModel) {

    }

}