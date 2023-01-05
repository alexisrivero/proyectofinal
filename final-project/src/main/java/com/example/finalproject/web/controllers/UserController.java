package com.example.finalproject.web.controllers;

import com.example.finalproject.service.implementation.UserServiceImplementation;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserServiceImplementation userServiceImplementation;

    @Operation(summary = "Used to get the user information that is currently authenticated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Obtained the information of the current user",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404",
            description = "User information not found",
            content = @Content)
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@AuthenticationPrincipal Jwt principal)
    {
        return userServiceImplementation.getUser(getEmailByPrincipal(principal));
    }


    @Operation(summary = "Used to get the all the addresses that are related to this user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Obtained all the addresses related to the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserAddressDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "There are no addresses related to this user",
                    content = @Content)
    })
    @GetMapping(value = "/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<UserAddressDTO> getAllUserAddresses(@AuthenticationPrincipal Jwt principal)
    {
        return userServiceImplementation.getAllAddresses(getEmailByPrincipal(principal));
    }

    @Operation(summary = "Used to create a new address related to the current authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create an address for this user",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Can't create an address for this authenticated user, information not found",
                    content = @Content)
    })
    @PostMapping(value = "/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createAddress (@AuthenticationPrincipal Jwt principal,@RequestBody @Valid CreateAddressDTO createAddressDTO)
    {
        userServiceImplementation.createAddress(getEmailByPrincipal(principal),createAddressDTO);
        return new ResponseEntity<>("Address Added Successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Used to get the all the payment methods that are related to this user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Obtained all the payment methods related to the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentMethodDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "There are no payment methods related to this user",
                    content = @Content)
    })
    @GetMapping(value = "/payments")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentMethodDTO> getAllUserPaymentMethods(@AuthenticationPrincipal Jwt principal)
    {
        return userServiceImplementation.getAllPaymentMethods(getEmailByPrincipal(principal));
    }

    @Operation(summary = "Used to create a new payment method related to the current authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create a payment method for this user",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Can't create a payment method for this authenticated user, information not found",
                    content = @Content)
    })
    @PostMapping(value = "/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createPaymentMethod (@AuthenticationPrincipal Jwt principal,@RequestBody @Valid CreatePaymentMethodDTO createPaymentMethodDTO)
    {
        userServiceImplementation.createPaymentMethod(getEmailByPrincipal(principal),createPaymentMethodDTO);
        return new ResponseEntity<>("Payment Method Added Successfully", HttpStatus.CREATED);
    }

    private String getEmailByPrincipal (Jwt principal)
    {
        return principal.getClaims().get("email").toString();
    }



}
