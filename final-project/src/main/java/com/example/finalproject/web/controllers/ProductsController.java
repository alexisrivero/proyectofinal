package com.example.finalproject.web.controllers;

import com.example.finalproject.service.implementation.ProductServiceImplementation;
import com.example.finalproject.web.DTO.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products")
public class ProductsController {

    private final ProductServiceImplementation productServiceImplementation;

    @Operation(summary = "Used to get all the products available on the store and their information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Obtained all the products and their information",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "There are no products available in this store",
                    content = @Content)
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getProducts()
    {
        return productServiceImplementation.getAllProducts();
    }

    @Operation(summary = "Used to get a specific product and its information based on its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Obtained the product information based on its id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "There is no product available with this id",
                    content = @Content)
    })
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProduct(@PathVariable long id)
    {
        return productServiceImplementation.getProduct(id);
    }

}
