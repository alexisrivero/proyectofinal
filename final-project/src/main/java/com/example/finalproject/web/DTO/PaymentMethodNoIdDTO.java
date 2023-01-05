package com.example.finalproject.web.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentMethodNoIdDTO {
//DTO used to show the payment method information
    private String name;

    private String paymentType;
}
