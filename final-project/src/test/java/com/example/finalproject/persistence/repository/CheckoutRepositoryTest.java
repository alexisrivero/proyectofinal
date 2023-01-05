package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.Checkout;
import com.example.finalproject.persistence.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("Checkout Repository Tests")
class CheckoutRepositoryTest {

    @Autowired
    private CheckoutRepository checkoutRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findByUser with valid user get the checkout")
    void findByUser_ValidUser_GetCheckout()
    {
        Checkout checkout =  objectCreator.createCheckout();
        checkout.getCheckoutProducts().removeAll(checkout.getCheckoutProducts());

        Checkout savedCheckout = checkoutRepository.save(checkout);

        Checkout findByUser = checkoutRepository.findByUser(savedCheckout.getUser());

        assertThat(findByUser,is(not(nullValue())));
        assertThat(findByUser,is(savedCheckout));
    }
}