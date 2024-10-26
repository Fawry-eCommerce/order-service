package com.fawry.order_service.service.client;

import com.fawry.order_service.model.ConsumeProductStockRequest;
import com.fawry.order_service.model.ConsumeProductStockResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StoreClient {

    private final RestTemplate restTemplate;

    @Value("${store.service.url}")
    private String storeServiceUrl;

    public StoreClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ConsumeProductStockResponse> consumeStock(List<ConsumeProductStockRequest> stockRequests) {
        try {
            ResponseEntity<List<ConsumeProductStockResponse>> response = restTemplate.exchange(
                    storeServiceUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(stockRequests),
                    new ParameterizedTypeReference<>() {
                    }
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error consuming product stock:");
        }
    }
}
