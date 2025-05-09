package com.example.shopping.Services;


import com.example.shopping.Entities.Orders;
import com.example.shopping.Entities.Product;
import com.example.shopping.Entities.Users;
import com.example.shopping.Repositories.OrderRepository;
import com.example.shopping.Repositories.ProductRepository;
import com.example.shopping.Repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsersRepository userRepository;

    public Optional<Orders> getOrderByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Users getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

	@Override
	public void save(Orders order) {
		orderRepository.save(order);
		
	}
	public List<Orders> getOrdersByStatus(String status) {
	    return orderRepository.findByStatus(status);
	}

}


    

	

