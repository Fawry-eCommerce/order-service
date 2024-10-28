package com.fawry.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
    private Long id;

    @Column(name = "coupon_code", length = 100)
    private String couponCode;

    @Column(name = "customer_email", length = 100)
    private String customerEmail;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "coupon_type", length = 50)
    private String couponType;

    @Column(name = "coupon_value")
    private Double couponValue;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

}
