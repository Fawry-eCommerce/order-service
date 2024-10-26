package com.fawry.order_service.service.client;

import com.fawry.order_service.model.CouponResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CouponClient {

    private final RestTemplate restTemplate;

    @Value("${coupon.service.url}")
    private String couponServiceUrl;
    public CouponClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CouponResponse validateCoupon(String couponCode) {
        ResponseEntity<CouponResponse> response;
        try {
            response = restTemplate.getForEntity(
                    String.format("%s?code=%s", couponServiceUrl, couponCode),
                    CouponResponse.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Can't call coupon Service");
        }
        return response.getBody();
    }

}
