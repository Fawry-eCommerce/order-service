package com.fawry.order_service.model;

import com.fawry.order_service.entity.Orders;
import lombok.Data;

@Data
public class OrderItemsModel {
    private Long id;
    private Orders order;
    private Long productId;
    private Integer quantity;
    private Double price;
}

