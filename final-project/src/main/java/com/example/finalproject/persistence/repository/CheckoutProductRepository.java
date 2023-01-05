package com.example.finalproject.persistence.repository;

import com.example.finalproject.persistence.model.Checkout;
import com.example.finalproject.persistence.model.CheckoutProduct;
import com.example.finalproject.persistence.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutProductRepository extends JpaRepository<CheckoutProduct,Long> {

    CheckoutProduct findByCheckoutAndProduct(Checkout checkout, Product product);

    void deleteAllByCheckout(Checkout checkout);
}
