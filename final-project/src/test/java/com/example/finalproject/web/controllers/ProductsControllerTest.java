package com.example.finalproject.web.controllers;

import com.example.finalproject.CustomArgumentResolver;
import com.example.finalproject.exception.GlobalExceptionHandler;
import com.example.finalproject.exception.ResourceNotFoundException;
import com.example.finalproject.persistence.model.Product;
import com.example.finalproject.ObjectCreator;
import com.example.finalproject.service.implementation.ProductServiceImplementation;
import com.example.finalproject.web.DTO.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

class ProductsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductServiceImplementation productServiceImplementation;

    private ObjectCreator objectCreator;

    private ProductsController productsController;

    @BeforeEach
    private void setUp()
    {
        productsController = new ProductsController(productServiceImplementation);
        objectCreator = new ObjectCreator();

        mockMvc = MockMvcBuilders
                .standaloneSetup(productsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Test if MockMvc Is Created")
    void shouldCreateMockMvc()
    {
        assertThat(mockMvc,is(not(nullValue())));
    }


    @Nested
    @DisplayName("getProduct")
    class GetProduct {
        @Test
        @DisplayName("getProduct Valid ID return ProductDTO and OKResponse")
        void getProduct_ValidId_OKResponse() throws Exception
        {
            ProductDTO productDTO = objectCreator.createProductDTO();

            when(productServiceImplementation.getProduct(anyLong())).thenReturn(productDTO);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("product"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1.00))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(10))
                    .andExpect(status().isOk());

            verify(productServiceImplementation).getProduct(anyLong());
        }

        @Test
        @DisplayName("getProduct Invalid ID Throw Exception")
        void getProduct_Invalid_IsNotFound() throws Exception
        {
            when(productServiceImplementation.getProduct(anyLong())).thenThrow(ResourceNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("getProducts")
    class GetProducts {
        @Test
        @DisplayName("getProducts Valid ID return ProductDTO and OKResponse")
        void getProducts_AvailableProducts_OKResponse() throws Exception
        {
            List<ProductDTO> productDTOList = new ArrayList<>();
            productDTOList.add(objectCreator.createProductDTO());

            when(productServiceImplementation.getAllProducts()).thenReturn(productDTOList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("product"))
                    .andExpect(status().isOk());

            verify(productServiceImplementation).getAllProducts();
        }

        @Test
        @DisplayName("getProducts NoProducts Throw Exception")
        void getProducts_NoProducts_IsNotFound() throws Exception
        {
            when(productServiceImplementation.getProduct(anyLong())).thenThrow(ResourceNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

}