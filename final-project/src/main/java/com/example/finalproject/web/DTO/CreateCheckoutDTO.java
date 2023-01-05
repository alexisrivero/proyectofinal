package com.example.finalproject.web.DTO;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCheckoutDTO {
//Dto used to create a checkout with basic information.

    @NotEmpty(message = "Add at least one product")
    @Valid
    private List<CreateCheckoutProductDTO> products;

}
