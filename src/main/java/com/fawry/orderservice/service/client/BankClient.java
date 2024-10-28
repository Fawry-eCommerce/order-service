package com.fawry.order_service.service.client;

import com.fawry.order_service.model.PaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankClient {

    private final RestTemplate restTemplate;

    @Value("${bank.service.url}")
    private String bankServiceUrl;

    public BankClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String processPayment(PaymentRequest paymentRequest) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    bankServiceUrl,
                    paymentRequest,
                    String.class
            );
            return response.getBody();
        }catch (Exception e){
            throw new RuntimeException("Can't call bank Service");
        }

    }
}
