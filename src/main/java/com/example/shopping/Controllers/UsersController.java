package com.example.shopping.Controllers;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.Entities.Product;
import com.example.shopping.Entities.Users;
import com.example.shopping.Repositories.UsersRepository;
import com.example.shopping.Services.UsersService;
import com.example.shopping.Services.productService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class UsersController {



    @Autowired
    UsersService userv;
    @Autowired
    UsersRepository urepo;
    @Autowired
    productService pserv;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody Users user, HttpSession session) {
        System.out.println("Received user: " + user); // Debugging log

        // Validate request data
        if (user == null || user.getEmail() == null) {
            return ResponseEntity.badRequest().body("Invalid request data"); // Corrected status code
        }

        // Check if email already exists
        if (userv.emailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        // Save the user in the database
        Users savedUser = userv.addUser(user); // Ensure user is saved first!

        // Store email in session
        session.setAttribute("email", savedUser.getEmail());
        System.out.println("Email stored in session: " + session.getAttribute("email"));

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> validateUser(@RequestBody Map<String, String> requestData, HttpSession session) {
        System.out.println("Entering login validation...");

        // Extract input
        String email = requestData.get("email");
        String password = requestData.get("password");
        String phone = requestData.get("phone");

        // Input validation
        if (password == null || (email == null && phone == null)) {
            System.out.println("Login failed: Missing email/phone or password.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or phone and password are required.");
        }

        // Authenticate with email
        if (email != null && userv.validateUser(email, password)) {
            Users user = userv.getUser(email);
            
            // Store both email & user ID in session
            session.setAttribute("email", user.getEmail());
            session.setAttribute("userId", user.getId());  
            System.out.println("Session stored: Email=" + session.getAttribute("email") + " | UserID=" + session.getAttribute("userId"));

            String role = userv.getRole(email);
            return ResponseEntity.ok(role.equalsIgnoreCase("admin") ? "AdminDashboard" : "CustomerDashboard");
        }

        // Authenticate with phone
        if (phone != null && userv.validatePhone(phone, password)) {
            Users user = userv.getUser(phone);
            
            // Store phone & user ID in session
            session.setAttribute("phone", phone);
            session.setAttribute("userId", user.getId());  
            System.out.println("Session stored: Phone=" + session.getAttribute("phone") + " | UserID=" + session.getAttribute("userId"));

            String role = userv.getRole(phone);
            return ResponseEntity.ok(role.equalsIgnoreCase("admin") ? "AdminDashboard" : "CustomerDashboard");
        }

        // Invalid credentials
        System.out.println("Login failed: Invalid credentials.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/phone or password.");
    }

    
    @GetMapping("/account-details")
    public ResponseEntity<Users> getAccountDetails(@RequestParam String email) {
        Users user = urepo.findByEmail(email); // Fetch user details from the database
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> handleForgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            userv.processForgotPassword(email); // Business logic
            return ResponseEntity.ok("Password reset request processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
   
    }





