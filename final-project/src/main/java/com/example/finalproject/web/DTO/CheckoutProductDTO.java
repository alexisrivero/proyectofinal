package com.example.finalproject.web.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutProductDTO {
//DTO used to show  only some information of the product checkout
    private String name;

    private double price;

    private int quantity;


}
