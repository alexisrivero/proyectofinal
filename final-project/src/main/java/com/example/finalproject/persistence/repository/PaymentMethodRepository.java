package com.example.finalproject.persistence.repository;

import com.example.finalproject.persistence.model.PaymentMethod;
import com.example.finalproject.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {

    PaymentMethod findByUserAndId(User user, Long id);

    List<PaymentMethod> findAllByUser(User user);
}
