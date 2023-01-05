package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.Address;
import com.example.finalproject.persistence.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("Address Repository Tests")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findById with valid id and user get the Address")
    void findByUserAndId_ValidIdAndUser_GetAddress()
    {
        Address address = objectCreator.createAddress();
        address.setUser(objectCreator.createUser());
        Address savedAddress = addressRepository.save(address);
        Address findByUserAndId = addressRepository.findByUserAndId(savedAddress.getUser(),savedAddress.getId());

        assertThat(findByUserAndId,is(not(nullValue())));
        assertThat(findByUserAndId,is(savedAddress));
    }

    @Test
    @DisplayName("findById with valid user get the address List")
    void findAllByUser_ValidUser_GetAddresList()
    {
        Address address = objectCreator.createAddress();
        address.setUser(objectCreator.createUser());
        Address savedAddress = addressRepository.save(address);

        List<Address> addressList = addressRepository.findAllByUser(savedAddress.getUser());

        assertThat(addressList.size(),is(not(0)));
        assertThat(addressList.get(0),is(savedAddress));
    }
}