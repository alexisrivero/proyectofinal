package com.example.finalproject.persistence.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @NotNull(message = "Please specify an address")
    private Address address;

    @OneToOne
    @NotNull(message = "Please specify a payment method")
    private PaymentMethod paymentMethod;

    @Column
    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @OneToOne
    private Transaction transaction;

    @Column
    @PositiveOrZero
    @NotNull
    private double total;


}
