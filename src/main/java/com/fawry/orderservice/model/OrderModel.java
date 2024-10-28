package com.fawry.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderModel {

    private Long id;

    private String couponCode;

    @Email(message = "Customer email should be valid")
    @NotBlank(message = "Customer email cannot be empty")
    private String customerEmail;

    private String referenceNumber;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    private String couponType;

    private Double couponValue;

    private Double totalAmount;

    private String transactionId;

    private LocalDateTime createdAt;

    private List<OrderItemsModel> orderItems;
}