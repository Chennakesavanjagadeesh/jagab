package com.example.shopping.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;



import com.example.shopping.Entities.Orders;

import com.example.shopping.Entities.Product;
import com.example.shopping.Entities.Users;
import com.example.shopping.Repositories.OrderRepository;
import com.example.shopping.Repositories.ProductRepository;
import com.example.shopping.Repositories.UsersRepository;
import com.example.shopping.Services.OrderService;
import com.example.shopping.Services.PaymentService;
import com.example.shopping.Services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
	@Autowired
	UsersService service;
	@Autowired
	ProductRepository prepo;
	@Autowired
	UsersRepository urepo;
	@Autowired
	OrderRepository orepo;
	@Autowired
	OrderService oserv;
	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) throws Exception {
	    int amount = Integer.parseInt(data.get("amount").toString());
	    int productId = Integer.parseInt(data.get("productId").toString());
	    int userId = Integer.parseInt(data.get("userId").toString());

	    // Create Razorpay client
	    RazorpayClient razorpayClient = new RazorpayClient("rzp_test_KPzd2OmHl1RINQ", "u2CRcRCRLR3pvFZ6A5gi7RY3");

	    // Prepare order request
	    JSONObject options = new JSONObject();
	    options.put("amount", amount * 100); // amount in paise
	    options.put("currency", "INR");
	    options.put("receipt", "txn_" + UUID.randomUUID());
	    options.put("payment_capture", true);

	    // Create order in Razorpay
	    Order razorpayOrder = razorpayClient.orders.create(options);

	    // ✅ Save order in your database
	    Orders order = new Orders();
	    order.setOrderId(razorpayOrder.get("id")); // Razorpay order ID
	    order.setAmount(amount);
	    order.setStatus("PENDING");

	    // Get Product and User from DB
	    Product product = prepo.findById(productId).orElse(null);
	    Users user = urepo.findById(userId).orElse(null);
	    order.setProduct(product);
	    order.setUser(user);

	    orepo.save(order); // ✅ Save to DB

	    // Send Razorpay order info to frontend
	    Map<String, String> response = new HashMap<>();
	    response.put("orderId", razorpayOrder.get("id"));
	    response.put("amount", String.valueOf(amount * 100));
	    response.put("currency", "INR");

	    return ResponseEntity.ok(response);
	}


	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId,
	                             @RequestParam String paymentId,
	                             @RequestParam String signature) {
	    try {
	        String secret = "u2CRcRCRLR3pvFZ6A5gi7RY3";
	        String data = orderId + "|" + paymentId;

	        boolean isValid = Utils.verifySignature(data, signature, secret);

	        if (isValid) {
	            Optional<Orders> orderOpt = oserv.getOrderByOrderId(orderId);
	            if (orderOpt.isPresent()) {
	                Orders order = orderOpt.get();
	                order.setPaymentId(paymentId);
	                order.setStatus("PAID");
	                oserv.save(order); // ✅ Make sure this method exists in OrderService
	            }
	        }

	        return isValid;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
	}

    



    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
        // Do nothing or log if you want
    }


}
