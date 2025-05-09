package com.example.shopping.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.Entities.PaymentDetails;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails, Long> {
    Optional<PaymentDetails> findByTransactionId(String transactionId); // ✅ Ensure this method exists
}
