package com.fawry.orderservice.service.impl;

import com.fawry.orderservice.entity.Orders;
import com.fawry.orderservice.exception.InvalidCouponException;
import com.fawry.orderservice.exception.OutOfStockException;
import com.fawry.orderservice.mapper.OrderMapper;
import com.fawry.orderservice.model.*;
import com.fawry.orderservice.repository.OrderRepository;
import com.fawry.orderservice.service.OrderService;
import com.fawry.orderservice.service.client.BankClient;
import com.fawry.orderservice.service.client.CouponClient;
import com.fawry.orderservice.service.client.StoreClient;
import com.fawry.orderservice.service.message.NotificationProducer;
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

    @Autowired
    private final NotificationProducer notificationProducer;

    public OrderServiceImpl(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }


    @Override
    public void createOrder(OrderModel orderModel) {
        consumeProductsInStock(orderModel);

        checkCouponAvailability(orderModel);

        orderModel.setReferenceNumber(UUID.randomUUID().toString());

        String transactionId = bankClient.processPayment(new PaymentRequest(orderModel.getCustomerEmail(), orderModel.getTotalAmount()));
        orderModel.setTransactionId(transactionId);

        Orders orders = orderMapper.mapModelToEntity(orderModel);
        orderRepository.save(orders);

        notification(orderModel);

    }

    private void notification(OrderModel orderModel) {
        OrderNotificationModel CustomerNotification = new OrderNotificationModel(
                orderModel.getCustomerEmail(),
                "Order Confirmation",
                "Your order has been successfully placed with reference number: " + orderModel.getReferenceNumber()
                        + " And Amount" +  orderModel.getTotalAmount()
        );

        OrderNotificationModel merchantNotification = new OrderNotificationModel(
                "khaledibrahimza@gmail.com",
                "Order Confirmation",
                "customer place order successfully with reference number: " + orderModel.getReferenceNumber()
                        + " And Amount" +  orderModel.getTotalAmount()
        );
        notificationProducer.sendOrderNotification(CustomerNotification);
        notificationProducer.sendOrderNotification(merchantNotification);
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
                        .map(item -> new ConsumeProductStockRequest(item.getProductId(), item.getQuantity()))
                        .collect(Collectors.toList());
        List<ConsumeProductStockResponse> stockResponse = storeClient.consumeStock(stockRequests);

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