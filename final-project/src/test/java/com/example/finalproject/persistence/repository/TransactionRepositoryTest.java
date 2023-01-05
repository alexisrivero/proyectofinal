package com.example.finalproject.persistence.repository;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.persistence.model.Transaction;
import com.example.finalproject.persistence.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("Transaction Repository Tests")
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        objectCreator = new ObjectCreator();
    }

    @Test
    @DisplayName("findById with valid id get the transaction")
    void findById_ValidId_GetTransaction()
    {
        Transaction savedTransaction = transactionRepository.save(objectCreator.createTransaction());
        Optional<Transaction> findById = transactionRepository.findById(savedTransaction.getId());

        assertThat(findById.get(),is(not(nullValue())));
        assertThat(findById.get(),is(savedTransaction));
    }
}