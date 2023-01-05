package com.example.finalproject.service;

import com.example.finalproject.web.DTO.*;

public interface CheckoutService {

    CheckoutDTO getCheckout(String email);
    void createCheckOut(String email,CreateCheckoutDTO checkoutDTO);

    void addProductToCheckout(String email, CreateCheckoutProductDTO checkoutProductDTO);

    void modifyCheckoutProductQuantity(String email,long productId, UpdateCheckoutProductDTO updateCheckoutProductDTO);

    void deleteCheckoutProduct(String email,long productID);

    void deleteCheckout(String email);

    void changeCheckoutAddress (String email,long id);

    void changeCheckoutPaymentMethod (String email,long id);



    void generateOrder(String email);

}
