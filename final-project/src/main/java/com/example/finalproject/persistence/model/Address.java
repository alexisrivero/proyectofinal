package com.example.finalproject.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @NotBlank(message = "House Number is Mandatory")
    private String houseNumber;

    @Column
    @NotBlank(message = "Street is Mandatory")
    private String street;

    @Column
    @NotBlank(message = "City is Mandatory")
    private String city;

    @Column
    @NotBlank(message = "State is Mandatory")
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
