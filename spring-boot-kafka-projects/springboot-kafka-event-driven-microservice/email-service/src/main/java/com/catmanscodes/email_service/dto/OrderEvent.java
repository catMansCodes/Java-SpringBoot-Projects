package com.catmanscodes.email_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEvent {

    private String orderEventId;
    private String orderStatus;
    private String orderType;
    private String orderDate;
    private String customerId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;

}
