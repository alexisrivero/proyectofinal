package com.example.finalproject.persistence.repository;

import com.example.finalproject.persistence.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
