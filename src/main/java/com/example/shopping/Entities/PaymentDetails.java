package com.example.shopping.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PaymentDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private String paymentId;
    private String status;
    private String method;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    // ✅ Default Constructor
    public PaymentDetails() {}

    // ✅ Field Constructor
    public PaymentDetails(String transactionId, String paymentId, String status, String method, double amount, Orders order, Users user) {
        this.transactionId = transactionId;
        this.paymentId = paymentId;
        this.status = status;
        this.method = method;
        this.amount = amount;
        this.order = order;
        this.user = user;
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    // ✅ toString Method
    @Override
    public String toString() {
        return "PaymentDetails{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", status='" + status + '\'' +
                ", method='" + method + '\'' +
                ", amount=" + amount +
                ", order=" + (order != null ? order.getOrderId() : "null") +
                ", user=" + (user != null ? user.getId() : "null") +
                '}';
    }
}
