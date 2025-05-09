package com.example.shopping.Controllers;

import com.example.shopping.Entities.Orders;
import com.example.shopping.Entities.Product;
import com.example.shopping.Entities.Users;
import com.example.shopping.Repositories.OrderRepository;
import com.example.shopping.Services.OrderService;
import com.example.shopping.Services.productService;
import com.example.shopping.Services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private productService productService;

    @Autowired
    UsersService userService;
    @Autowired
    OrderRepository orepo;

    @GetMapping("/order-success")
    public Map<String, Object> getOrderSuccessPage(@RequestParam String orderId, @RequestParam String paymentId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Fetch the order using the orderId
            Optional<Orders> orderOptional = orderService.getOrderByOrderId(orderId);
            
            // Check if order is present
            if (!orderOptional.isPresent()) {
                response.put("error", "Order not found.");
                return response;
            }

            Orders order = orderOptional.get();

            // Ensure paymentId matches
            if (!order.getPaymentId().equals(paymentId)) {
                response.put("error", "Invalid payment ID.");
                return response;
            }

            // Fetch product details from the product associated with the order
            Product product = order.getProduct();
            if (product == null) {
                response.put("error", "Product not found.");
                return response;
            }

            // Fetch user details (optional, if needed)
            Users user = order.getUser();
            if (user == null) {
                response.put("error", "User not found.");
                return response;
            }

            // Add order details and product details to the response
            response.put("orderDetails", order);
            response.put("product", product);
            response.put("user", user);

            return response;

        } catch (Exception e) {
            response.put("error", "An error occurred while processing the request.");
            return response;
        }
    }
    @GetMapping("/admin/orders")
    public List<Orders> getPaidOrders() {
        return orderService.getOrdersByStatus("PAID");
    }
    @GetMapping("/api/orders/by-email/{email}")
    public ResponseEntity<List<Orders>> getOrdersByEmail(@PathVariable String email) {
        List<Orders> orders = orepo.findByUserEmail(email);
        return ResponseEntity.ok(orders);
    }



}
