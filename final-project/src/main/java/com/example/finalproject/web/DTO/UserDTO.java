package com.example.finalproject.web.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDTO {
    //DTO used for showing important information about the user

    @Schema(description = "Email of the user that is authenticated",
    example = "test@test.com", required = true)
    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private List<CreateAddressDTO> address;

    private List<PaymentMethodNoIdDTO> paymentMethods;

}
