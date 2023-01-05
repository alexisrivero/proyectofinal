package com.example.finalproject.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CheckoutProduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    @NotNull(message = "Stock is Mandatory")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "checkout_id")
    @JsonIgnore
    private Checkout checkout;

}
