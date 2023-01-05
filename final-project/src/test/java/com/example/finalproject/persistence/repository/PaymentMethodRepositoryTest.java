package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.PaymentMethod;
import com.example.finalproject.persistence.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("PaymentMethod Repository Tests")
class PaymentMethodRepositoryTest {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findById with valid id and user get the payment method")
    void findByUserAndId_ValidIdAndUser_GetPaymentMethod()
    {
        PaymentMethod paymentMethod = objectCreator.createPaymentMethod();
        paymentMethod.setUser(objectCreator.createUser());
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        PaymentMethod findByUserAndId = paymentMethodRepository.findByUserAndId(savedPaymentMethod.getUser(),savedPaymentMethod.getId());

        assertThat(findByUserAndId,is(not(nullValue())));
        assertThat(findByUserAndId,is(savedPaymentMethod));
    }

    @Test
    @DisplayName("findById with valid user get the payment method List")
    void findAllByUser_ValidUser_GetPaymentMethodList()
    {
        PaymentMethod paymentMethod = objectCreator.createPaymentMethod();
        paymentMethod.setUser(objectCreator.createUser());
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);

        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAllByUser(savedPaymentMethod.getUser());

        assertThat(paymentMethodList.size(),is(not(0)));
        assertThat(paymentMethodList.get(0),is(savedPaymentMethod));
    }
}