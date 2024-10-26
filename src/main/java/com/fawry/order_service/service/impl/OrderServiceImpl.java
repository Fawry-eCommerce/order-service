package com.fawry.order_service.service.impl;

import com.fawry.order_service.entity.Orders;
import com.fawry.order_service.exception.InvalidCouponException;
import com.fawry.order_service.exception.OutOfStockException;
import com.fawry.order_service.mapper.OrderMapper;
import com.fawry.order_service.model.*;
import com.fawry.order_service.repository.OrderRepository;
import com.fawry.order_service.service.OrderService;
import com.fawry.order_service.service.client.BankClient;
import com.fawry.order_service.service.client.CouponClient;
import com.fawry.order_service.service.client.StoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String PERCENTAGE = "PERCENTAGE";
    public static final String FIXED = "FIXED";
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    StoreClient storeClient;
    @Autowired
    CouponClient couponClient;
    @Autowired
    BankClient bankClient;

    @Override
    public void createOrder(OrderModel orderModel) {
        //1-check availability and consume orderItems in stock
        consumeProductsInStock(orderModel);

        checkCouponAvailability(orderModel);

        // Step 3: Generate reference number
        orderModel.setReferenceNumber(UUID.randomUUID().toString());

        // Step 4: Process payment with bank
        String transactionId = bankClient.processPayment(new PaymentRequest(orderModel.getCustomerEmail(), orderModel.getTotalAmount()));
        orderModel.setTransactionId(transactionId);

        // Step 5: Save the order
        Orders orders = orderMapper.mapModelToEntity(orderModel);
        orderRepository.save(orders);
    }

    private void checkCouponAvailability(OrderModel orderModel) {
        if (orderModel.getCouponCode() != null) {
            CouponResponse couponResponse = couponClient.validateCoupon(orderModel.getCouponCode());
            if (!couponResponse.isCouponValid()) {
                throw new InvalidCouponException("Invalid coupon code");
            }

            String couponType = null;
            double orderAmountAfterDiscount = orderModel.getAmount();
            double discountValue = 0;
            if (couponResponse.getCouponPercentage() != null) {
                couponType = PERCENTAGE;
                discountValue = (orderModel.getAmount() * couponResponse.getCouponPercentage()) / 100;
                orderAmountAfterDiscount = orderModel.getAmount() - discountValue;

            } else if (couponResponse.getCouponValue() != null) {
                couponType = FIXED;
                discountValue = orderModel.getAmount() - couponResponse.getCouponValue();
                orderAmountAfterDiscount = orderModel.getAmount() - couponResponse.getCouponValue();
            }

            orderModel.setTotalAmount(orderAmountAfterDiscount);
            orderModel.setCouponType(couponType);
            orderModel.setCouponValue(discountValue);
        }
    }

    private void consumeProductsInStock(OrderModel orderModel) {
        List<ConsumeProductStockRequest> stockRequests =
                orderModel
                        .getOrderItems()
                        .stream()
                        .map(item -> new ConsumeProductStockRequest(item.getProductCode(), item.getQuantity()))
                        .collect(Collectors.toList());
        List<ConsumeProductStockResponse> stockResponse = storeClient.consumeStock(stockRequests);

        // Check stock response to validate availability
        if (stockResponse.stream().anyMatch(response -> !response.isAvailable())) {
            throw new OutOfStockException("One or more items are out of stock");
        }
    }


    //1-check availability and consume orderItems in stock
    //2-if coupon code not null then check coupon with coupon service
    //3-generate reference number and set to order model
    //4-send the customer email and merchant account number to Bank, the bank will return the transaction id.
    //5-save order
    //6- send to notification api the message to send to customer and merchant
}