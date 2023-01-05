package com.example.finalproject.web.controllers;

import com.example.finalproject.service.implementation.OrderServiceImplementation;
import com.example.finalproject.web.DTO.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users/orders")
public class OrderController {

    private final OrderServiceImplementation orderServiceImplementation;

    @Operation(summary = "Used to get all the orders related to the current authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Obtained all the orders information related to the current user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "There are no orders related to the current user",
                    content = @Content)
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrders(@AuthenticationPrincipal Jwt principal)
    {
        return orderServiceImplementation.getAllOrders(getEmailByPrincipal(principal));
    }

    @Operation(summary = "Used to get a specific order related to the current authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Obtained a specific order related to the current user based on the id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "There is no order related to this user with this id",
                    content = @Content)
    })
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrder(@AuthenticationPrincipal Jwt principal,@PathVariable long id)
    {
        return orderServiceImplementation.getOrder(getEmailByPrincipal(principal),id);
    }

    private String getEmailByPrincipal (Jwt principal)
    {
        return principal.getClaims().get("email").toString();
    }
}
