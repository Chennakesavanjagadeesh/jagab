package com.example.shopping.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private double price;

    private double discount; // Default to 0 if not provided
    private int quantity;
    private String color; // Optional: Set max length for description
    private String description;
    private String category; 
    private String imageUrl; // To store the file path of the image
	public Product() {}

    // Parameterized constructor
    public Product(String name, String brand, Double price, Double discount, Integer quantity, String color, String description, String imageUrl, String category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }

	public Product(Long id, String name, double price, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscountedPrice() {
	    // Cap discount between 0 and 100
	    if (this.discount < 0 || this.discount > 100) {
	        return this.price; // Ignore invalid discounts
	    }
	    return this.price - (this.price * this.discount / 100);
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	
	public double getDiscount() {
		return discount;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", price=" + price + ", discount="
				+ discount + ", quantity=" + quantity + ", color=" + color + ", description=" + description
				+ ", category=" + category + ", imageUrl=" + imageUrl + "]";
	}
	
	
	

}