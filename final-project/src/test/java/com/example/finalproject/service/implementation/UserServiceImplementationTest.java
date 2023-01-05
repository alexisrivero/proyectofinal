package com.example.finalproject.service.implementation;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.exception.ResourceNotFoundException;
import com.example.finalproject.persistence.model.Address;
import com.example.finalproject.persistence.model.PaymentMethod;
import com.example.finalproject.persistence.model.User;
import com.example.finalproject.persistence.repository.AddressRepository;

import com.example.finalproject.persistence.repository.PaymentMethodRepository;
import com.example.finalproject.persistence.repository.UserRepository;
import com.example.finalproject.web.DTO.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    private UserServiceImplementation userServiceImplementation;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        userServiceImplementation = new UserServiceImplementation(userRepository,addressRepository,paymentMethodRepository);
        objectCreator = new ObjectCreator();
    }

    @Nested
    @DisplayName("getUser")
    class getUser {

        @Test
        @DisplayName("getUser When valid email return valid UserDTO")
        void getUser_ValidUserEmail_GetUserDTO()
        {
            User user = objectCreator.createUser();
            UserDTO userDTO = objectCreator.createUserDTO();
            
            when(userRepository.findByEmail(anyString())).thenReturn(user);
            
            UserDTO getUserDTO = userServiceImplementation.getUser("alexisr15494@gmail.com");

            assertThat(getUserDTO.getEmail(),is(userDTO.getEmail()));
            assertThat(getUserDTO.getUserName(),is(userDTO.getUserName()));
            assertThat(getUserDTO.getAddress().size(),is(userDTO.getAddress().size()));
            assertThat(getUserDTO.getPaymentMethods().size(),is(userDTO.getPaymentMethods().size()));

            verify(userRepository).findByEmail(anyString());
        }

        @Test
        @DisplayName("getUser When invalid email throw exception")
        void getUser_InValidUserEmail_ThrowException()
        {
            when(userRepository.findByEmail(anyString())).thenReturn(null);

            assertThrows(ResourceNotFoundException.class,() ->
                    userServiceImplementation.getUser("email@test.com"));

            verify(userRepository).findByEmail(anyString());
        }
    }

    @Nested
    @DisplayName("getAllPaymentMethods")
    class getAllPaymentMethods {
        @Test
        @DisplayName("getAllPaymentMethods When valid email return List Payment Method")
        void getAllPaymentMethods_ValidUserEmail_ListPaymentMethods()
        {
            User user = objectCreator.createUser();
            List<PaymentMethod> paymentMethodList = objectCreator.createPaymentMethodList();
            List<PaymentMethodDTO> paymentMethodDTOList = objectCreator.createPaymentMethodDTOList();

            when(userRepository.findByEmail(anyString())).thenReturn(user);
            when(paymentMethodRepository.findAllByUser(user)).thenReturn(paymentMethodList);

            List<PaymentMethodDTO> getAllPaymentMethodsDTO= userServiceImplementation.getAllPaymentMethods("test@test.com");

            assertThat(getAllPaymentMethodsDTO.size(),is(paymentMethodDTOList.size()));

            verify(userRepository).findByEmail(anyString());
            verify(paymentMethodRepository).findAllByUser(user);
        }

        @Test
        @DisplayName("getAllPaymentMethods When no payment methods Throw Exception")
        void getAllPaymentMethods_NoPaymentMethodAvailable_ThrowException()
        {
            User user = objectCreator.createUser();
            List<PaymentMethod> paymentMethodList = new ArrayList<>();
            when(userRepository.findByEmail(anyString())).thenReturn(user);
            when(paymentMethodRepository.findAllByUser(user)).thenReturn(paymentMethodList);

            assertThrows(ResourceNotFoundException.class,() ->
                    userServiceImplementation.getAllPaymentMethods("email@test.com"));

            verify(userRepository).findByEmail(anyString());
            verify(paymentMethodRepository).findAllByUser(user);
        }
    }

    @Test
    @DisplayName("createPaymentMethod When valid dto create a payment method")
    void createPaymentMethod_ValidCreatePaymentMethodDTO_CreatePaymentMethod()
    {
        User user = objectCreator.createUser();
        CreatePaymentMethodDTO createPaymentMethodDTO = objectCreator.createCreatePaymentMethodDTO();

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        userServiceImplementation.createPaymentMethod("test@test.com",createPaymentMethodDTO);

        verify(userRepository).findByEmail(anyString());
    }

    @Nested
    @DisplayName("getAllAddresses")
    class getAllAddresses {
        @Test
        @DisplayName("getAllAddresses When valid email return List Addresses")
        void getAllAddresses_ValidUserEmail_ListAddresses()
        {
            User user = objectCreator.createUser();
            List<Address> addressList = objectCreator.createAddressList();

            List<UserAddressDTO> userAddressDTOList = objectCreator.createUserAddressDTOList();

            when(userRepository.findByEmail(anyString())).thenReturn(user);
            when(addressRepository.findAllByUser(user)).thenReturn(addressList);

            List<UserAddressDTO> getUserAddressDtoList =
                    userServiceImplementation.getAllAddresses("test@test.com");

            assertThat(getUserAddressDtoList.size(),is(userAddressDTOList.size()));

            verify(userRepository).findByEmail(anyString());
            verify(addressRepository).findAllByUser(user);
        }

        @Test
        @DisplayName("getAllAddresses When No address available Throw Exception")
        void getAllAddresses_NoAddressAvailable_ThrowException()
        {
            User user = objectCreator.createUser();
            List<Address> addressList = new ArrayList<>();

            when(userRepository.findByEmail(anyString())).thenReturn(user);
            when(addressRepository.findAllByUser(user)).thenReturn(addressList);

            assertThrows(ResourceNotFoundException.class,() ->
                    userServiceImplementation.getAllAddresses("email@test.com"));

            verify(userRepository).findByEmail(anyString());
            verify(addressRepository).findAllByUser(user);
        }
    }

    @Test
    @DisplayName("createAddress When valid CreateAddressDTO create new address")
    void createAddress_ValidCreateAddressDTO_CreateAddress()
    {
        User user = objectCreator.createUser();
        CreateAddressDTO createAddressDTO = objectCreator.createCreateAddressDTO();

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        userServiceImplementation.createAddress("test@test.com",createAddressDTO);

        verify(userRepository).findByEmail(anyString());
    }



}