package com.example.shopping.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.Entities.Orders;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	Optional<Orders> findByOrderId(String orderId);
	List<Orders> findByStatus(String status);
	List<Orders> findByUserEmail(String email);

    
	}
