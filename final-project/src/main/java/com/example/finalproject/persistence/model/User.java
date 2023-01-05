package com.example.finalproject.persistence.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @NotBlank(message = "Email is Mandatory")
    @Email(message = "Email must be valid")
    private String email;

    @Column
    @NotBlank(message = "Username Name is Mandatory")
    private String userName;

    @Column
    @NotBlank(message = "Firstname Name is Mandatory")
    private String firstName;

    @Column
    @NotBlank(message = "Lastname Name is Mandatory")
    private String lastName;

    @Column
    @Pattern(regexp = "^[+]\\D*(503)\\D*(\\d{4})\\D*(\\d{4})" , message = "Please enter a valid phone number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<Address> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<PaymentMethod> paymentMethods;

    @OneToOne(mappedBy = "user")
    private Checkout checkout;

}
