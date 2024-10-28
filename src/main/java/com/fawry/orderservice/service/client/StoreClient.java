package com.fawry.order_service.service.client;

import com.fawry.order_service.model.ConsumeProductStockRequest;
import com.fawry.order_service.model.ConsumeProductStockResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
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
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<List<ConsumeProductStockRequest>> requestEntity = new HttpEntity<>(stockRequests, headers);

            ResponseEntity<List<ConsumeProductStockResponse>> response = restTemplate.exchange(
                    storeServiceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<ConsumeProductStockResponse>>() {
                    }
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error consuming product stock: " + e.getMessage(), e);
        }
    }
}
