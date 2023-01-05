package com.example.finalproject.web.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDTO {
//Dto used for creating an address in the checkout

    @NotBlank(message = "House Number is Mandatory")
    private String houseNumber;

    @NotBlank(message = "Street is Mandatory")
    private String street;

    @NotBlank(message = "City is Mandatory")
    private String city;

    @NotBlank(message = "State is Mandatory")
    private String state;

}

