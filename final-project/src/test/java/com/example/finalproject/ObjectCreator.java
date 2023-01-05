package com.example.finalproject;

import com.example.finalproject.persistence.model.*;
import com.example.finalproject.web.DTO.*;

import java.util.ArrayList;
import java.util.List;

public class ObjectCreator {

    //USER
    public User createUser ()
    {
        return User.builder()
                .id(1L)
                .email("test@test.com")
                .userName("tester")
                .firstName("Alexis")
                .lastName("Rivero")
                .phoneNumber("+503 1234 6789")
                .address(createAddressList())
                .paymentMethods(createPaymentMethodList())
                .build();
    }

    public User createUserNullElements ()
    {
        return User.builder()
                .id(1L)
                .email("test@test.com")
                .userName("tester")
                .firstName("Alexis")
                .lastName("Rivero")
                .phoneNumber("+503 12345 6789")
                .address(new ArrayList<>())
                .paymentMethods(new ArrayList<>())
                .build();
    }

    public UserDTO createUserDTO ()
    {
        return UserDTO.builder()
                .email(("test@test.com"))
                .userName("tester")
                .firstName("Alexis")
                .lastName("Rivero")
                .phoneNumber("+503 12345 6789")
                .address(createCreateAddressDTOList())
                .paymentMethods(createPaymentMethodNoIdDTOList())
                .build();


    }

    //ADDRESS
    public List<Address> createAddressList()
    {
        List<Address> addresses = new ArrayList<>();
        addresses.add(createAddress());
        return addresses;
    }
    public Address createAddress()
    {
        return Address.builder()
                .id(1L)
                .houseNumber("house")
                .street("street")
                .city("city")
                .state("state")
                .build();
    }

    public List<CreateAddressDTO> createCreateAddressDTOList()
    {
        List<CreateAddressDTO> createAddressDTOList = new ArrayList<>();
        createAddressDTOList.add(createCreateAddressDTO());
        return createAddressDTOList;
    }


    public CreateAddressDTO createCreateAddressDTO()
    {
        return CreateAddressDTO.builder()
                .houseNumber("house")
                .street("street")
                .city("city")
                .state("state")
                .build();
    }

    public List<UserAddressDTO> createUserAddressDTOList()
    {
        List<UserAddressDTO> userAddressDTOList = new ArrayList<>();
        userAddressDTOList.add(createUserAddressDTO());
        return userAddressDTOList;
    }

    public UserAddressDTO createUserAddressDTO()
    {
        return UserAddressDTO.builder()
                .id(1L)
                .houseNumber("house")
                .street("street")
                .city("city")
                .state("state")
                .build();
    }

    //PAYMENT METHOD
    public List<PaymentMethod> createPaymentMethodList()
    {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(createPaymentMethod());
        return paymentMethods;
    }
    public PaymentMethod createPaymentMethod()
    {
        return PaymentMethod.builder()
                .id(1L)
                .name("name")
                .founds(100.00)
                .paymentType("type")
                .build();
    }

    public CreatePaymentMethodDTO createCreatePaymentMethodDTO()
    {
        return CreatePaymentMethodDTO.builder()
                .name("name")
                .founds(100.00)
                .paymentType("type")
                .build();
    }

    public List<PaymentMethodNoIdDTO> createPaymentMethodNoIdDTOList()
    {
        List<PaymentMethodNoIdDTO> paymentMethodNoIdDTOList = new ArrayList<>();
        paymentMethodNoIdDTOList.add(createPaymentMethodNoIdDTO());
        return paymentMethodNoIdDTOList;
    }

    public PaymentMethodNoIdDTO createPaymentMethodNoIdDTO ()
    {
        return PaymentMethodNoIdDTO.builder()
                .name("name")
                .paymentType("type")
                .build();
    }

    public List<PaymentMethodDTO> createPaymentMethodDTOList()
    {
        List<PaymentMethodDTO> paymentMethodDTOS = new ArrayList<>();
        paymentMethodDTOS.add(createPaymentMethodDTO());
        return paymentMethodDTOS;
    }

    public PaymentMethodDTO createPaymentMethodDTO ()
    {
        return PaymentMethodDTO.builder()
                .id(1L)
                .name("name")
                .paymentType("type")
                .build();
    }

    //PRODUCT OBJECTS
    public Product createProduct()
    {
        return Product.builder()
                .id(1L)
                .name("product")
                .stock(10)
                .price(1.00)
                .build();
    }

    public ProductDTO createProductDTO ()
    {
        return ProductDTO.builder()
                .id(1L)
                .name("product")
                .stock(10)
                .price(1.00)
                .build();
    }

    //ORDER

    public Orders createOrder ()
    {
        return Orders.builder()
                .id(1L)
                .user(createUser())
                .address(createAddress())
                .paymentMethod(createPaymentMethod())
                .orderProducts(createOrderProducts())
                .total(1.00)
                .build();
    }

    public OrderDTO createOrderDTO ()
    {
        return OrderDTO.builder()
                .id(1L)
                .firstName("Alexis")
                .lastName("Rivero")
                .address(createCreateAddressDTO())
                .orderProducts(createOrderProductDTOList())
                .paymentMethod(createPaymentMethodNoIdDTO())
                .total(1.00)
                .build();
    }

    //ORDER PRODUCT
    public List<OrderProduct> createOrderProducts()
    {
        List<OrderProduct> orderProducts = new ArrayList<>();
        OrderProduct orderProduct = OrderProduct.builder()
                .id(1L)
                .product(createProduct())
                .quantity(1)
                .build();
        orderProducts.add(orderProduct);
        return orderProducts;
    }

    public List<OrderProductDTO> createOrderProductDTOList()
    {
        List<OrderProductDTO> list = new ArrayList<>();
        OrderProductDTO orderProductDTO = createOrderProductDTO();
        list.add(orderProductDTO);
        return list;
    }

    public OrderProductDTO createOrderProductDTO()
    {
        return OrderProductDTO.builder()
                .name("product")
                .price(1.00)
                .quantity(1)
                .build();
    }

    public OrderProduct createOrderProduct()
    {
        return OrderProduct.builder()
                .id(1L)
                .product(createProduct())
                .quantity(1)
                .build();
    }

    //Checkout
    public Checkout createCheckout ()
    {
        return Checkout.builder()
                .id(1L)
                .user(createUser())
                .checkoutProducts(createCheckoutProductList())
                .address(createAddress())
                .paymentMethod(createPaymentMethod())
                .build();
    }

    public Checkout createCheckoutEmptyFields()
    {
        return Checkout.builder()
                .id(1L)
                .user(createUser())
                .checkoutProducts(createCheckoutProductList())
                .build();
    }

    public CheckoutDTO createCheckoutDTO()
    {
        return CheckoutDTO.builder()
                .userName("tester")
                .firstName("Alexis")
                .lastName("Rivero")
                .checkoutProducts(createCheckoutProductDTOList())
                .address(createCreateAddressDTO())
                .paymentMethod(createPaymentMethodNoIdDTO())
                .subTotal(1.00)
                .build();
    }

    public CreateCheckoutDTO createCreateCheckoutDTO()
    {
        return CreateCheckoutDTO.builder()
                .products(createCreateCheckoutProductDTOList())
                .build();

    }

    //CHECKOUT PRODUCTS
    public List<CheckoutProduct> createCheckoutProductList ()
    {
        List<CheckoutProduct> checkoutProductList = new ArrayList<>();
        checkoutProductList.add(createCheckoutProduct());
        return checkoutProductList;
    }
    public CheckoutProduct createCheckoutProduct ()
    {
        return CheckoutProduct.builder()
                .id(1L)
                .product(createProduct())
                .quantity(1)
                .build();
    }

    public List<CreateCheckoutProductDTO> createCreateCheckoutProductDTOList()
    {
        List<CreateCheckoutProductDTO> createCheckoutProductDTOList = new ArrayList<>();
        createCheckoutProductDTOList.add(createCreateCheckoutProductDTO());
        return createCheckoutProductDTOList;
    }

    public CreateCheckoutProductDTO createCreateCheckoutProductDTO ()
    {
        return CreateCheckoutProductDTO.builder()
                .id(1L)
                .quantity(1)
                .build();
    }

    public List<CheckoutProductDTO> createCheckoutProductDTOList()
    {
        List<CheckoutProductDTO> checkoutProductDTOList = new ArrayList<>();
        checkoutProductDTOList.add(createCheckoutProductDTO());
        return checkoutProductDTOList;
    }
    public CheckoutProductDTO createCheckoutProductDTO()
    {
        return CheckoutProductDTO.builder()
                .name("product")
                .price(1.00)
                .quantity(1)
                .build();
    }

    public UpdateCheckoutProductDTO createUpdateCheckoutProductDTO(int value)
    {
        return UpdateCheckoutProductDTO.builder()
                .quantity(value)
                .build();
    }

    //TRANSACTION
    public Transaction createTransaction()
    {
        return Transaction.builder()
                .id(1L)
                .quantity(1.00)
                .paymentMethod(createPaymentMethod())
                .build();
    }
}
