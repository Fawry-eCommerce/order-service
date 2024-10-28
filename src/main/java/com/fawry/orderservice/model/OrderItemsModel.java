package com.fawry.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemsModel {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price;
}

