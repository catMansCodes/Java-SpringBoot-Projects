package com.catmanscodes.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEvent {

    private String orderEventId;

    @NotBlank(message = "orderStatus is required")
    private String orderStatus;

    @NotBlank(message = "orderType is required")
    private String orderType;

    private String orderDate;

    @NotBlank(message = "customerId is required")
    private String customerId;

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotBlank(message = "customerPhone is required")
    private String customerPhone;

    @NotBlank(message = "customerAddress is required")
    private String customerAddress;

}
