package com.example.finalproject.web.controllers;

import com.example.finalproject.CustomArgumentResolver;
import com.example.finalproject.ObjectCreator;
import com.example.finalproject.exception.GlobalExceptionHandler;
import com.example.finalproject.exception.ResourceNotFoundException;
import com.example.finalproject.service.implementation.OrderServiceImplementation;
import com.example.finalproject.service.implementation.UserServiceImplementation;
import com.example.finalproject.web.DTO.OrderDTO;
import com.example.finalproject.web.DTO.UserAddressDTO;
import com.example.finalproject.web.DTO.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderServiceImplementation orderServiceImplementation;

    private ObjectCreator objectCreator;

    private OrderController orderController;

    @BeforeEach
    private void setUp()
    {
        orderController = new OrderController(orderServiceImplementation);
        objectCreator = new ObjectCreator();

        mockMvc = MockMvcBuilders
                .standaloneSetup(orderController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new CustomArgumentResolver())
                .build();
    }

    @Nested
    @DisplayName("getAllOrders")
    class GetAllOrders {
        @Test
        @DisplayName("getAllOrders Valid Email return List order dto and OKResponse")
        void getAllOrders_ValidEmail_OKResponse() throws Exception
        {
            List<OrderDTO> orderDTOList = new ArrayList<>();
            orderDTOList.add(objectCreator.createOrderDTO());

            when(orderServiceImplementation.getAllOrders(anyString())).thenReturn(orderDTOList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/orders"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Alexis"))
                    .andExpect(status().isOk());

            verify(orderServiceImplementation).getAllOrders(anyString());
        }

        @Test
        @DisplayName("getAllOrders No orders Throw Exception")
        void getAllOrders_NoOrders_IsNotFoundResponse() throws Exception
        {

            when(orderServiceImplementation.getAllOrders(anyString())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/orders"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(orderServiceImplementation).getAllOrders(anyString());
        }
    }

    @Nested
    @DisplayName("getOrder")
    class GetOrder {
        @Test
        @DisplayName("getOrder Valid Email return OrderDTO and OKResponse")
        void getOrder_ValidEmail_OKResponse() throws Exception
        {
            OrderDTO orderDTO = objectCreator.createOrderDTO();

            when(orderServiceImplementation.getOrder(anyString(),anyLong())).thenReturn(orderDTO);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/orders/1"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Alexis"))
                    .andExpect(status().isOk());

            verify(orderServiceImplementation).getOrder(anyString(),anyLong());
        }

        @Test
        @DisplayName("getOrder InValid Email Throw Exception")
        void getOrder_InValidId_IsNotFound() throws Exception
        {

            when(orderServiceImplementation.getOrder(anyString(),anyLong())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/orders/1"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(orderServiceImplementation).getOrder(anyString(),anyLong());
        }
    }
}