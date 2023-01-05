package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.Product;
import com.example.finalproject.persistence.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("Product Repository Tests")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findById with valid id get the product")
    void findById_ValidId_GetProduct()
    {
        Product savedProduct = productRepository.save(objectCreator.createProduct());
        Optional<Product> findById = productRepository.findById(savedProduct.getId());

        assertThat(findById.get(),is(not(nullValue())));
        assertThat(findById.get(),is(savedProduct));
    }
}