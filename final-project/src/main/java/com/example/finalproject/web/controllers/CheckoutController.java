package com.example.finalproject.web.controllers;

import com.example.finalproject.service.implementation.CheckoutServiceImplementation;
import com.example.finalproject.web.DTO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users/checkouts")
public class CheckoutController {
    private final CheckoutServiceImplementation checkoutServiceImplementation;
    @Operation(summary = "Used to get the checkout that is related to the current authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Obtained the checkout information of the current user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckoutDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Could not find a checkout related to the current user",
                    content = @Content)
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CheckoutDTO getCheckout(@AuthenticationPrincipal Jwt principal)
    {
        return checkoutServiceImplementation.getCheckout(getEmailByPrincipal(principal));
    }

    @Operation(summary = "Used to create a checkout for the current user using a product or a list of products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create a checkout for the user with a product or a list of products",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not create a checkout for the current user",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Could not create a checkout because the current user already has a checkout",
                    content = @Content)
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCheckout (@AuthenticationPrincipal Jwt principal,@RequestBody @Valid CreateCheckoutDTO checkoutDTO)
    {
        checkoutServiceImplementation.createCheckOut(getEmailByPrincipal(principal),checkoutDTO);
        return new ResponseEntity<>("Checkout successfully created", HttpStatus.CREATED);
    }

    @Operation(summary = "Used to add a product to a current checkout or create a checkout with the specified product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create and/or add a checkout product for the checkout of the user",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not add a product to the checkout because did not find the product to add",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Could not add the product because there was not enough stock, or the information" +
                            " sent was invalid",
                    content = @Content)
    })
    @PostMapping(value = "/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addProductToCheckout (@AuthenticationPrincipal Jwt principal,@RequestBody @Valid CreateCheckoutProductDTO checkoutProductDTO)
    {
        checkoutServiceImplementation.addProductToCheckout(getEmailByPrincipal(principal),checkoutProductDTO);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Used to add or remove the quantity of a product in the checkout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully modified the quantity of a product in the checkout",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not find the product on the checkout",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Could not modify the quantity because there is not enough stock",
                    content = @Content)
    })
    @PutMapping(value = "/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> modifyCheckoutProductQuantity (@AuthenticationPrincipal Jwt principal,@PathVariable long id, @RequestBody @Valid UpdateCheckoutProductDTO checkoutProductDTO)
    {
        checkoutServiceImplementation.modifyCheckoutProductQuantity(getEmailByPrincipal(principal),id,checkoutProductDTO);
        return new ResponseEntity<>("Product quantity modified successfully", HttpStatus.OK);
    }

    @Operation(summary = "Used to delete a product from the checkout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully deleted the product from the checkout",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not find the product to eliminate on the checkout",
                    content = @Content)
    })
    @DeleteMapping(value = "/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteCheckoutProduct(@AuthenticationPrincipal Jwt principal,@PathVariable long id)
    {
        checkoutServiceImplementation.deleteCheckoutProduct(getEmailByPrincipal(principal),id);
        return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.OK);
    }

    @Operation(summary = "Used to delete the checkout related to the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully deleted the checkout related to the user",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not find checkout related to the user",
                    content = @Content)
    })
    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteCheckout(@AuthenticationPrincipal Jwt principal)
    {
        checkoutServiceImplementation.deleteCheckout(getEmailByPrincipal(principal));
        return new ResponseEntity<>("Checkout Deleted Successfully",HttpStatus.OK);
    }

    @Operation(summary = "Used to change the address delivery of the checkout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Changed the delivery address of the checkout",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not find the address related to the user",
                    content = @Content)
    })
    @PutMapping(value = "/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> changeCheckoutAddress (@AuthenticationPrincipal Jwt principal,@PathVariable long id)
    {
        checkoutServiceImplementation.changeCheckoutAddress(getEmailByPrincipal(principal),id);
        return new ResponseEntity<>("Address Changed Successfully", HttpStatus.OK);
    }

    @Operation(summary = "Used to change the payment method of the checkout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Changed the payment method of the checkout",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not find the payment method related to the user",
                    content = @Content)
    })
    @PutMapping(value = "/payments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> changeCheckoutPaymentMethod (@AuthenticationPrincipal Jwt principal,@PathVariable long id)
    {
        checkoutServiceImplementation.changeCheckoutPaymentMethod(getEmailByPrincipal(principal),id);
        return new ResponseEntity<>("Payment Method Changed Successfully", HttpStatus.OK);
    }

    @Operation(summary = "Used to generate an order based on the checkout information of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully generated an order based on the checkout of the user",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Could not find the checkout to generate the order",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "A required field is empty or there are not enough stocks or founds to " +
                            "generate the order",
                    content = @Content)
    })
    @PostMapping(value = "/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> generateOrder (@AuthenticationPrincipal Jwt principal)
    {
        checkoutServiceImplementation.generateOrder(getEmailByPrincipal(principal));
        return new ResponseEntity<>("Order Successfully Generated", HttpStatus.CREATED);
    }

    private String getEmailByPrincipal (Jwt principal)
    {
        return principal.getClaims().get("email").toString();
    }

}
