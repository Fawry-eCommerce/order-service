package com.fawry.order_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemsModel {
    private Long id;
    private Long productId;
    private String productCode;
    private Integer quantity;
    private Double price;
}

