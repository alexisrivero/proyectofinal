package com.example.finalproject.web.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCheckoutProductDTO {
//DTO to update the checkout product quantity

    @Schema(description = "Quantity to add or remove from the product on the checkout",
            example = "1", required = true)
    @NotNull(message = "Quantity is mandatory")
    private int quantity;

}
