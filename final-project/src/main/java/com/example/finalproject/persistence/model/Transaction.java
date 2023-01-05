package com.example.finalproject.persistence.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @PositiveOrZero(message = "Quantity must be positive number")
    @NotNull(message = "quantity is mandatory")
    private double quantity;

    @ManyToOne
    private PaymentMethod paymentMethod;
}
