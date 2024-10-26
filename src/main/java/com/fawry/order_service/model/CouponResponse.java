package com.fawry.order_service.model;

import lombok.Data;

@Data
public class CouponResponse {
    boolean isCouponValid;
    Double couponValue;
    Integer couponPercentage;
}
