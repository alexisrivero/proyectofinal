package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.Checkout;
import com.example.finalproject.persistence.model.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("Order Repository Tests")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findByUserAndId with valid user and id get the order")
    void findByUserAndId_ValidUserAndID_GetOrder()
    {
        Orders order =  objectCreator.createOrder();
        order.getOrderProducts().removeAll(order.getOrderProducts());

        Orders savedOrder = orderRepository.save(order);

        Orders findByUserAndId = orderRepository.findByUserAndId(savedOrder.getUser(),savedOrder.getId());

        assertThat(findByUserAndId,is(not(nullValue())));
        assertThat(findByUserAndId,is(savedOrder));
    }

    @Test
    @DisplayName("findAllByUser with valid user get the order list")
    void findAllByUser_ValidUser_GetOrderList()
    {
        Orders order =  objectCreator.createOrder();
        order.getOrderProducts().removeAll(order.getOrderProducts());
        Orders savedOrder = orderRepository.save(order);

        List<Orders> findAllByUser = orderRepository.findAllByUser(savedOrder.getUser());

        assertThat(findAllByUser.size(),is(not(0)));
        assertThat(findAllByUser.get(0),is(savedOrder));
    }
}