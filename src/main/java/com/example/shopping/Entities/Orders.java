package com.example.shopping.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Orders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id") // Foreign key reference to Product
    private Product product;

    private String orderId;
    private String paymentId;
    private int quantity;
    private double amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Foreign key reference to Users
    private Users user;

    // Default constructor
    public Orders() {}

    // Constructor for initializing the order with values
    public Orders(Long id, Product product, String orderId, String paymentId, int quantity, double amount,
                  String status, Users user) {
        this.id = id;
        this.product = product;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Orders [id=" + id + ", product=" + product + ", orderId=" + orderId + ", paymentId=" + paymentId
                + ", quantity=" + quantity + ", amount=" + amount + ", status=" + status + ", user=" + user + "]";
    }
}
