package com.example.finalproject.web.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CheckoutDTO {
//DTO to show on JSON all the info of a checkout

    private String userName;

    private String firstName;

    private String lastName;

    private List<CheckoutProductDTO> checkoutProducts;

    private CreateAddressDTO address;

    private PaymentMethodNoIdDTO paymentMethod;

    private double subTotal;
}
