package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.Checkout;
import com.example.finalproject.persistence.model.CheckoutProduct;
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
@DisplayName("CheckoutProduct Repository Tests")
class CheckoutProductRepositoryTest {

    @Autowired
    private CheckoutProductRepository checkoutProductRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findByCheckoutAndProduct with valid checkout and product get the checkout product")
    void findByCheckoutAndProduct_ValidCheckoutAndProduct_GetCheckoutProduct()
    {


        CheckoutProduct checkoutProduct = objectCreator.createCheckoutProduct();
        CheckoutProduct savedCheckoutProduct = checkoutProductRepository.save(checkoutProduct);


        CheckoutProduct getCheckoutProduct = checkoutProductRepository.findByCheckoutAndProduct
                (savedCheckoutProduct.getCheckout(),savedCheckoutProduct.getProduct());

        assertThat(getCheckoutProduct,is(not(nullValue())));
        assertThat(getCheckoutProduct,is(savedCheckoutProduct));
    }

    @Test
    @DisplayName("deleteAllByCheckout with valid checkout Delete All")
    void deleteAllByCheckout_ValidCheckout_DeleteAll()
    {
        CheckoutProduct checkoutProduct = objectCreator.createCheckoutProduct();
        CheckoutProduct savedCheckoutProduct = checkoutProductRepository.save(checkoutProduct);

        Checkout savedCheckout = checkoutRepository.save(objectCreator.createCheckout());
        savedCheckoutProduct.setCheckout(savedCheckout);

        checkoutProductRepository.deleteAllByCheckout(savedCheckout);

        Optional<CheckoutProduct> getCheckoutProduct = checkoutProductRepository.findById(savedCheckoutProduct.getId());

        assertThat(getCheckoutProduct.isPresent(),is(false));
    }
}