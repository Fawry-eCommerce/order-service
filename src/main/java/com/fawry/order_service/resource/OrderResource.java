package com.fawry.order_service.resource;

import com.fawry.order_service.entity.Orders;
import com.fawry.order_service.model.OrderModel;
import com.fawry.order_service.service.OrderService;
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
