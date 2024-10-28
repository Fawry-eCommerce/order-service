package com.fawry.orderservice.model;

import lombok.Data;

@Data
public class CouponResponse {
    boolean isCouponValid;
    Double couponValue;
    Integer couponPercentage;
}
