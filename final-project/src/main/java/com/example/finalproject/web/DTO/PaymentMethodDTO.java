package com.example.finalproject.web.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class PaymentMethodDTO {
//DTO used to show the payment method with id and without the founds

    private long id;

    private String name;

    private String paymentType;
}
