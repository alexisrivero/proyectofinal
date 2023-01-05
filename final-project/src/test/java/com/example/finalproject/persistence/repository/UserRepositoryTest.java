package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.User;
import com.example.finalproject.service.implementation.UserServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("User Repository Tests")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findByEmail with valid email get the user")
    void findByEmail_ValidEmail_GetUser()
    {
        User savedUser = userRepository.save(objectCreator.createUser());
        User findByEmail = userRepository.findByEmail(savedUser.getEmail());

        assertThat(findByEmail,is(not(nullValue())));
        assertThat(findByEmail,is(savedUser));
    }

    @Test
    @DisplayName("findByEmail with invalid email null")
    void findByEmail_InValidEmail_Null()
    {
        User savedUser = userRepository.save(objectCreator.createUser());
        User findByEmail = userRepository.findByEmail("invalid@invalid.com");

        assertThat(findByEmail,is(nullValue()));
    }
}