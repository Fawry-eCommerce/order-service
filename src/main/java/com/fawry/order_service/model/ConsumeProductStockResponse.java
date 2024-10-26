package com.fawry.order_service.model;

import lombok.Data;

@Data
public class ConsumeProductStockResponse {
    private String code;
    private int availableQuantity;
    private boolean isAvailable;
}