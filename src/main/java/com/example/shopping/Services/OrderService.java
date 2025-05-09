package com.example.shopping.Services;

import java.util.List;
import java.util.Optional;

import com.example.shopping.Entities.Orders;
import com.example.shopping.Entities.Product;
import com.example.shopping.Entities.Users;

public interface OrderService {
	public Optional<Orders> getOrderByOrderId(String orderId);

	public Product getProductById(Long productId);
	public Users getUserById(Long userId) ;

	public void save(Orders order);
	public List<Orders> getOrdersByStatus(String status);
}