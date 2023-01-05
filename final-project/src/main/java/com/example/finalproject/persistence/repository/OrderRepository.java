package com.example.finalproject.persistence.repository;

import com.example.finalproject.persistence.model.Orders;
import com.example.finalproject.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findAllByUser(User user);

    Orders findByUserAndId(User user, long id);
}
