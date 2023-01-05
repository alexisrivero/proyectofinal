package com.example.finalproject.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "OrderProduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    @NotNull(message = "Stock is Mandatory")
    @PositiveOrZero
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders order;

}
