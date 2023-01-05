package com.example.finalproject.web.controllers;

import com.example.finalproject.CustomArgumentResolver;
import com.example.finalproject.ObjectCreator;
import com.example.finalproject.exception.GlobalExceptionHandler;
import com.example.finalproject.exception.ResourceNotFoundException;
import com.example.finalproject.service.implementation.UserServiceImplementation;
import com.example.finalproject.web.DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImplementation userServiceImplementation;

    private ObjectCreator objectCreator;

    private UserController userController;

    @BeforeEach
    private void setUp()
    {
        userController = new UserController(userServiceImplementation);
        objectCreator = new ObjectCreator();

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new CustomArgumentResolver())
                .build();
    }

    @Nested
    @DisplayName("getUsers")
    class GetUsers {
        @Test
        @DisplayName("getUsers Valid Email return UserDTO and OKResponse")
        void getUsers_ValidEmail_OKResponse() throws Exception
        {
            UserDTO userDTO = objectCreator.createUserDTO();

            when(userServiceImplementation.getUser(anyString())).thenReturn(userDTO);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Alexis"))
                    .andExpect(status().isOk());

            verify(userServiceImplementation).getUser(anyString());
        }

        @Test
        @DisplayName("getUsers InValid Email Throw Exception")
        void getUsers_InValidEmail_IsNotFound() throws Exception
        {

            when(userServiceImplementation.getUser(anyString())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(userServiceImplementation).getUser(anyString());
        }
    }

    @Nested
    @DisplayName("getAllUserAddresses")
    class GetAllUserAddresses {
        @Test
        @DisplayName("getAllUserAddresses Valid Email return List user address dto and OKResponse")
        void getAllUserAddresses_ValidEmail_OKResponse() throws Exception
        {
            List<UserAddressDTO> userAddressDTOList = new ArrayList<>();
            userAddressDTOList.add(objectCreator.createUserAddressDTO());

            when(userServiceImplementation.getAllAddresses(anyString())).thenReturn(userAddressDTOList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/addresses"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].houseNumber").value("house"))
                    .andExpect(status().isOk());

            verify(userServiceImplementation).getAllAddresses(anyString());
        }

        @Test
        @DisplayName("getAllUserAddresses No Addresses Throw Exception")
        void getAllUserAddresses_NoAddresses_IsNotFoundResponse() throws Exception
        {

            when(userServiceImplementation.getAllAddresses(anyString())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/addresses"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(userServiceImplementation).getAllAddresses(anyString());
        }
    }

    @Nested
    @DisplayName("createAddress")
    class CreateAddress {

        @Test
        @DisplayName("createAddress Valid CreateAddressDTO Return Is Created")
        void createAddress_ValidCreateAddressDTO_IsCreatedResponse() throws Exception
        {
            CreateAddressDTO createAddressDTO = objectCreator.createCreateAddressDTO();
            doNothing().when(userServiceImplementation).createAddress(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/addresses")
                            .content(new ObjectMapper().writeValueAsString(createAddressDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());

            verify(userServiceImplementation).createAddress(anyString(),any());
        }

        @Test
        @DisplayName("createAddress Invalid Email Return Is Not Found")
        void createAddress_InvalidEmail_NotFoundResponse() throws Exception
        {
            CreateAddressDTO createAddressDTO = objectCreator.createCreateAddressDTO();
            doThrow(ResourceNotFoundException.class).when(userServiceImplementation).createAddress(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/addresses")
                            .content(new ObjectMapper().writeValueAsString(createAddressDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(userServiceImplementation).createAddress(anyString(),any());
        }
    }

    @Nested
    @DisplayName("getAllUserPaymentMethods")
    class GetAllUserPaymentMethods {
        @Test
        @DisplayName("getAllUserPaymentMethods Valid Email return UserDTO and OKResponse")
        void getAllUserPaymentMethods_ValidEmail_OKResponse() throws Exception
        {
            List<PaymentMethodDTO> paymentMethodDTOList = new ArrayList<>();
            paymentMethodDTOList.add(objectCreator.createPaymentMethodDTO());

            when(userServiceImplementation.getAllPaymentMethods(anyString())).thenReturn(paymentMethodDTOList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/payments"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("name"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentType").value("type"))
                    .andExpect(status().isOk());

            verify(userServiceImplementation).getAllPaymentMethods(anyString());
        }

        @Test
        @DisplayName("getAllUserPaymentMethods No Addresses Throw Exception")
        void getAllUserPaymentMethods_NoPaymentMethod_IsNotFoundResponse() throws Exception
        {
            when(userServiceImplementation.getAllPaymentMethods(anyString())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/payments"))
                    .andDo(print())
                    .andExpect(status().isNotFound());
            verify(userServiceImplementation).getAllPaymentMethods(anyString());
        }
    }

    @Nested
    @DisplayName("createPaymentMethod")
    class CreatePaymentMethod {

        @Test
        @DisplayName("createAddress Valid CreatePaymentMethodDTO Return Is Created")
        void createPaymentMethod_ValidCreatePaymentMethodDTO_IsCreatedResponse() throws Exception
        {
            CreatePaymentMethodDTO createPaymentMethodDTO = objectCreator.createCreatePaymentMethodDTO();
            doNothing().when(userServiceImplementation).createPaymentMethod(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/payments")
                            .content(new ObjectMapper().writeValueAsString(createPaymentMethodDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());

            verify(userServiceImplementation).createPaymentMethod(anyString(),any());
        }

        @Test
        @DisplayName("createAddress Valid CreateAddressDTO Return Is Created")
        void createPaymentMethod_InvalidEmail_NotFoundResponse() throws Exception
        {
            CreatePaymentMethodDTO createPaymentMethodDTO = objectCreator.createCreatePaymentMethodDTO();
            doThrow(ResourceNotFoundException.class).when(userServiceImplementation).createPaymentMethod(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/payments")
                            .content(new ObjectMapper().writeValueAsString(createPaymentMethodDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(userServiceImplementation).createPaymentMethod(anyString(),any());
        }
    }
}