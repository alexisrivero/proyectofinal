package com.example.finalproject.persistence.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @NotBlank(message = "Name is Mandatory")
    private String name;


    @Column
    @NotNull(message = "Price is Mandatory")
    @PositiveOrZero(message = "price must be a positive value")
    private double price;

    @Column
    @NotNull(message = "Stock is Mandatory")
    @PositiveOrZero(message = "stock must be a positive value")
    private int stock;

}
