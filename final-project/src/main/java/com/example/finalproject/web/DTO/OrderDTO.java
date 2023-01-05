package com.example.finalproject.web.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderDTO {
//Dto used to show the information of an specific order

    private long id;

    private String firstName;

    private String lastName;

    private CreateAddressDTO address;

    private PaymentMethodNoIdDTO paymentMethod;

    private List<OrderProductDTO> orderProducts;

    private double total;

}
