package com.example.finalproject.persistence.repository;

import com.example.finalproject.persistence.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository  extends JpaRepository<OrderProduct,Long> {
}
