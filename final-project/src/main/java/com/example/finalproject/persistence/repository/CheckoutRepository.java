package com.example.finalproject.persistence.repository;

import com.example.finalproject.persistence.model.Checkout;
import com.example.finalproject.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
    Checkout findByUser(User user);

}
