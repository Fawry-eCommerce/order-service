package com.fawry.orderservice.service.message;

import com.fawry.orderservice.model.OrderNotificationModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String notificationExchange;
    private final String notificationRoutingKey;

    public NotificationProducer(RabbitTemplate rabbitTemplate,
                                @Value("${rabbitmq.exchange.notification}") String notificationExchange,
                                @Value("${rabbitmq.routingkey.notification}") String notificationRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationExchange = notificationExchange;
        this.notificationRoutingKey = notificationRoutingKey;
    }

    public void sendOrderNotification(OrderNotificationModel notification) {
        rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notification);
    }
}
