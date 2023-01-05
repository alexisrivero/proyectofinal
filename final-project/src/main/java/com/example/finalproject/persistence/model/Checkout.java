package com.example.finalproject.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Checkout")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column
    @OneToMany(mappedBy = "checkout")
    private List<CheckoutProduct>checkoutProducts;

    @OneToOne
    private Address address;

    @OneToOne
    private PaymentMethod paymentMethod;

}
