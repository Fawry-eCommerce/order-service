package com.fawry.orderservice.resource;

import com.fawry.orderservice.model.OrderModel;
import com.fawry.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderResource {
    @Autowired
    OrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody @Valid OrderModel orderModel) {
        orderService.createOrder(orderModel);
    }
}
