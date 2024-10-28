package com.fawry.order_service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotificationModel implements Serializable {
    private String email;
    private String subject;
    private String message;

}
