package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.OrderProduct;
import com.example.finalproject.persistence.model.Orders;
import com.example.finalproject.persistence.model.Transaction;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("OrderProduct Repository Tests")
class OrderProductRepositoryTest {

    @Autowired
    private OrderProductRepository orderProductRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findById with valid id get the OrderProduct")
    void findById_ValidId_GetOrderProduct()
    {
        OrderProduct savedOrderProduct = orderProductRepository.save(objectCreator.createOrderProduct());
        Optional<OrderProduct> findById = orderProductRepository.findById(savedOrderProduct.getId());

        assertThat(findById.get(),is(not(nullValue())));
        assertThat(findById.get(),is(savedOrderProduct));
    }
}