package com.example.finalproject.web.controllers;

import com.example.finalproject.CustomArgumentResolver;
import com.example.finalproject.ObjectCreator;
import com.example.finalproject.exception.*;
import com.example.finalproject.service.implementation.CheckoutServiceImplementation;
import com.example.finalproject.service.implementation.OrderServiceImplementation;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CheckoutServiceImplementation checkoutServiceImplementation;

    private ObjectCreator objectCreator;

    private CheckoutController checkoutController;

    @BeforeEach
    private void setUp()
    {
        checkoutController = new CheckoutController(checkoutServiceImplementation);
        objectCreator = new ObjectCreator();

        mockMvc = MockMvcBuilders
                .standaloneSetup(checkoutController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new CustomArgumentResolver())
                .build();
    }

    @Nested
    @DisplayName("getCheckout")
    class GetCheckout {
        @Test
        @DisplayName("getCheckout Valid Email return CheckoutDTO and OKResponse")
        void getCheckout_ValidEmail_OKResponse() throws Exception
        {
            CheckoutDTO checkoutDTO = objectCreator.createCheckoutDTO();

            when(checkoutServiceImplementation.getCheckout(anyString())).thenReturn(checkoutDTO);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/checkouts"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Alexis"))
                    .andExpect(status().isOk());

            verify(checkoutServiceImplementation).getCheckout(anyString());
        }

        @Test
        @DisplayName("getCheckout InValid Email Throw Exception")
        void getCheckout_InValidEmail_IsNotFound() throws Exception
        {

            when(checkoutServiceImplementation.getCheckout(anyString())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/users/checkouts"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).getCheckout(anyString());
        }
    }

    @Nested
    @DisplayName("createCheckout")
    class createCheckout {
        @Test
        @DisplayName("createCheckout Valid CreateCheckoutDTO Return Is Created")
        void createCheckout_ValidCreateCheckoutDTO_IsCreatedResponse() throws Exception
        {
            CreateCheckoutDTO createCheckoutDTO = objectCreator.createCreateCheckoutDTO();
            doNothing().when(checkoutServiceImplementation).createCheckOut(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts")
                            .content(new ObjectMapper().writeValueAsString(createCheckoutDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Checkout successfully created"));

            verify(checkoutServiceImplementation).createCheckOut(anyString(),any());
        }

        @Test
        @DisplayName("createCheckout Invalid Email Is Not Found")
        void createCheckout_InvalidEmail_NotFoundResponse() throws Exception
        {
            CreateCheckoutDTO createCheckoutDTO = objectCreator.createCreateCheckoutDTO();
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).createCheckOut(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts")
                            .content(new ObjectMapper().writeValueAsString(createCheckoutDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).createCheckOut(anyString(),any());
        }

        @Test
        @DisplayName("createCheckout Existing Checkout Bad Request")
        void createCheckout_ExistingCheckout_BadRequest() throws Exception
        {
            CreateCheckoutDTO createCheckoutDTO = objectCreator.createCreateCheckoutDTO();
            doThrow(ResourceAlreadyExistException.class).when(checkoutServiceImplementation).createCheckOut(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts")
                            .content(new ObjectMapper().writeValueAsString(createCheckoutDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(checkoutServiceImplementation).createCheckOut(anyString(),any());
        }

        @Test
        @DisplayName("createCheckout Invalid DTO Bad Request")
        void createCheckout_InvalidDTO_BadRequest() throws Exception
        {
            CreateCheckoutDTO createCheckoutDTO = new CreateCheckoutDTO();

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts")
                            .content(new ObjectMapper().writeValueAsString(createCheckoutDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("addProductToCheckout")
    class AddProductToCheckout {
        @Test
        @DisplayName("addProductToCheckout Valid CheckoutProductDTO Return Is Created")
        void addProductToCheckout_ValidCheckoutProductDTO_IsCreatedResponse() throws Exception
        {
            CheckoutProductDTO checkoutProductDTO = objectCreator.createCheckoutProductDTO();
            doNothing().when(checkoutServiceImplementation).addProductToCheckout(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/products")
                            .content(new ObjectMapper().writeValueAsString(checkoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Product added successfully"));

            verify(checkoutServiceImplementation).addProductToCheckout(anyString(),any());
        }
        @Test
        @DisplayName("addProductToCheckout Invalid Email Is Not Found")
        void addProductToCheckoutt_InvalidEmail_NotFoundResponse() throws Exception
        {
            CheckoutProductDTO checkoutProductDTO = objectCreator.createCheckoutProductDTO();
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).addProductToCheckout(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/products")
                            .content(new ObjectMapper().writeValueAsString(checkoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).addProductToCheckout(anyString(),any());
        }

        @Test
        @DisplayName("addProductToCheckout Not enough Stocks Is Bad Request")
        void addProductToCheckout_NotEnoughStock_BadRequest() throws Exception
        {
            CheckoutProductDTO checkoutProductDTO = objectCreator.createCheckoutProductDTO();
            doThrow(NotEnoughStockException.class).when(checkoutServiceImplementation).addProductToCheckout(anyString(),any());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/products")
                            .content(new ObjectMapper().writeValueAsString(checkoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(checkoutServiceImplementation).addProductToCheckout(anyString(),any());
        }

        @Test
        @DisplayName("addProductToCheckout Invalid DTO Bad Request")
        void addProductToCheckout_InvalidDTO_BadRequest() throws Exception
        {
            CheckoutProductDTO checkoutProductDTO = new CheckoutProductDTO();

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts")
                            .content(new ObjectMapper().writeValueAsString(checkoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("modifyCheckoutProductQuantity")
    class ModifyCheckoutProductQuantity {
        @Test
        @DisplayName("modifyCheckoutProductQuantity Valid UpdateCheckoutProductDTO Return IsOK")
        void modifyCheckoutProductQuantity_UpdateCheckoutProductDTO_IsOKResponse() throws Exception
        {
            UpdateCheckoutProductDTO updateCheckoutProductDTO = objectCreator.createUpdateCheckoutProductDTO(1);
            doNothing().when(checkoutServiceImplementation).modifyCheckoutProductQuantity(anyString(),anyLong(),any());

            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/products/1")
                            .content(new ObjectMapper().writeValueAsString(updateCheckoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("Product quantity modified successfully"));

            verify(checkoutServiceImplementation).modifyCheckoutProductQuantity(anyString(),anyLong(),any());
        }

        @Test
        @DisplayName("modifyCheckoutProductQuantity Element not found Is Not Found")
        void modifyCheckoutProductQuantity_ElementNotFound_NotFoundResponse() throws Exception
        {
            UpdateCheckoutProductDTO updateCheckoutProductDTO = objectCreator.createUpdateCheckoutProductDTO(1);
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).modifyCheckoutProductQuantity(anyString(),anyLong(),any());
            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/products/1")
                            .content(new ObjectMapper().writeValueAsString(updateCheckoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).modifyCheckoutProductQuantity(anyString(),anyLong(),any());
        }

        @Test
        @DisplayName("modifyCheckoutProductQuantity Not enough Stocks Is Bad Request")
        void modifyCheckoutProductQuantity_NotEnoughStock_BadRequest() throws Exception
        {
            CheckoutProductDTO checkoutProductDTO = objectCreator.createCheckoutProductDTO();
            doThrow(NotEnoughStockException.class).when(checkoutServiceImplementation).modifyCheckoutProductQuantity(anyString(),anyLong(),any());

            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/products/1")
                            .content(new ObjectMapper().writeValueAsString(checkoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(checkoutServiceImplementation).modifyCheckoutProductQuantity(anyString(),anyLong(),any());
        }

        @Test
        @DisplayName("modifyCheckoutProductQuantity Invalid DTO Bad Request")
        void modifyCheckoutProductQuantity_InvalidDTO_BadRequest() throws Exception
        {
            CheckoutProductDTO checkoutProductDTO = null;

            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/products/1")
                            .content(new ObjectMapper().writeValueAsString(checkoutProductDTO))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("deleteCheckoutProduct")
    class DeleteCheckoutProduct {
        @Test
        @DisplayName("deleteCheckoutProduct Valid Id Return IsOK")
        void deleteCheckoutProduct_ValidId_IsOKResponse() throws Exception
        {
            doNothing().when(checkoutServiceImplementation).deleteCheckoutProduct(anyString(),anyLong());

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/checkouts/products/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("Product Deleted Successfully"));

            verify(checkoutServiceImplementation).deleteCheckoutProduct(anyString(),anyLong());
        }

        @Test
        @DisplayName("deleteCheckoutProduct Element not found Is Not Found")
        void deleteCheckoutProduct_ElementNotFound_NotFoundResponse() throws Exception
        {
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).deleteCheckoutProduct(anyString(),anyLong());

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/checkouts/products/1"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).deleteCheckoutProduct(anyString(),anyLong());
        }
    }

    @Nested
    @DisplayName("deleteCheckout")
    class DeleteCheckout {
        @Test
        @DisplayName("deleteCheckout Valid Id Return IsOK")
        void deleteCheckout_ValidId_IsOKResponse() throws Exception
        {
            doNothing().when(checkoutServiceImplementation).deleteCheckout(anyString());

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/checkouts"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("Checkout Deleted Successfully"));

            verify(checkoutServiceImplementation).deleteCheckout(anyString());
        }

        @Test
        @DisplayName("deleteCheckoutProduct Element not found Is Not Found")
        void deleteCheckoutProduct_ElementNotFound_NotFoundResponse() throws Exception
        {
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).deleteCheckout(anyString());

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/checkouts"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).deleteCheckout(anyString());
        }
    }

    @Nested
    @DisplayName("changeCheckoutAddress")
    class ChangeCheckoutAddress {
        @Test
        @DisplayName("changeCheckoutAddress Valid Id Return IsOK")
        void changeCheckoutAddress_ValidId_IsOKResponse() throws Exception
        {
            doNothing().when(checkoutServiceImplementation).changeCheckoutAddress(anyString(),anyLong());

            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/addresses/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("Address Changed Successfully"));

            verify(checkoutServiceImplementation).changeCheckoutAddress(anyString(),anyLong());
        }
        @Test
        @DisplayName("changeCheckoutAddress Element not found Is Not Found")
        void changeCheckoutAddress_ElementNotFound_NotFoundResponse() throws Exception
        {
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).changeCheckoutAddress(anyString(),anyLong());

            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/addresses/1"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).changeCheckoutAddress(anyString(),anyLong());
        }
    }

    @Nested
    @DisplayName("changeCheckoutPaymentMethod")
    class ChangeCheckoutPaymentMethod {
        @Test
        @DisplayName("changeCheckoutPaymentMethod Valid Id Return IsOK")
        void changeCheckoutPaymentMethod_ValidId_IsOKResponse() throws Exception
        {
            doNothing().when(checkoutServiceImplementation).changeCheckoutPaymentMethod(anyString(),anyLong());

            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/payments/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("Payment Method Changed Successfully"));

            verify(checkoutServiceImplementation).changeCheckoutPaymentMethod(anyString(),anyLong());
        }
        @Test
        @DisplayName("changeCheckoutPaymentMethod Element not found Is Not Found")
        void changeCheckoutPaymentMethod_ElementNotFound_NotFoundResponse() throws Exception
        {
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).changeCheckoutPaymentMethod(anyString(),anyLong());

            mockMvc.perform(MockMvcRequestBuilders.put("/api/users/checkouts/payments/1"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).changeCheckoutPaymentMethod(anyString(),anyLong());
        }
    }

    @Nested
    @DisplayName("generateOrder")
    class GenerateOrder {
        @Test
        @DisplayName("changeCheckoutPaymentMethod Valid Email Return IsCreated")
        void changeCheckoutPaymentMethod_ValidEmail_IsOKResponse() throws Exception
        {
            doNothing().when(checkoutServiceImplementation).generateOrder(anyString());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/purchases"))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Order Successfully Generated"));

            verify(checkoutServiceImplementation).generateOrder(anyString());
        }

        @Test
        @DisplayName("changeCheckoutPaymentMethod Element not found Is Not Found")
        void changeCheckoutPaymentMethod_ElementNotFound_NotFoundResponse() throws Exception
        {
            doThrow(ResourceNotFoundException.class).when(checkoutServiceImplementation).generateOrder(anyString());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/purchases"))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(checkoutServiceImplementation).generateOrder(anyString());
        }

        @Test
        @DisplayName("changeCheckoutPaymentMethod Null Required Info BadRequest")
        void changeCheckoutPaymentMethod_NullRequiredInfo_BadRequest() throws Exception
        {
            doThrow(RequiredInformationNullException.class).when(checkoutServiceImplementation).generateOrder(anyString());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/purchases"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(checkoutServiceImplementation).generateOrder(anyString());
        }

        @Test
        @DisplayName("changeCheckoutPaymentMethod Not Enough Stock Bad Request")
        void changeCheckoutPaymentMethod_NotEnoughStock_BadRequest() throws Exception
        {
            doThrow(NotEnoughStockException.class).when(checkoutServiceImplementation).generateOrder(anyString());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/purchases"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(checkoutServiceImplementation).generateOrder(anyString());
        }

        @Test
        @DisplayName("changeCheckoutPaymentMethod Not Enough Founds Bad Request")
        void changeCheckoutPaymentMethod_NotEnoughFounds_BadRequest() throws Exception
        {
            doThrow(NotEnoughFoundsException.class).when(checkoutServiceImplementation).generateOrder(anyString());

            mockMvc.perform(MockMvcRequestBuilders.post("/api/users/checkouts/purchases"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(checkoutServiceImplementation).generateOrder(anyString());
        }
    }
}