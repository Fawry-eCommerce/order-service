package com.fawry.order_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderModel {

    @NotNull(message = "ID cannot be null")
    private Long id;


    private String couponCode;

    @Email(message = "Customer email should be valid")
    @NotBlank(message = "Customer email cannot be empty")
    private String customerEmail;

    private String referenceNumber;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private Double amount;

    private String couponType;

    private Double couponValue;

    @NotNull(message = "Total amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than zero")
    private Double totalAmount;

    private String transactionId;

    private LocalDateTime createdAt;

    private List<OrderItemsModel> orderItems;
}