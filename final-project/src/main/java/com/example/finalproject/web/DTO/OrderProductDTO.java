package com.example.finalproject.web.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderProductDTO {
//DTO to show information of an specific order Product
    private String name;

    private double price;

    private int quantity;
}
