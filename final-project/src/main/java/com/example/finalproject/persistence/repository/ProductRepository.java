package com.example.finalproject.persistence.repository;

import com.example.finalproject.persistence.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
