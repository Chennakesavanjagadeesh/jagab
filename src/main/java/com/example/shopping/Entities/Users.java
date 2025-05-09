package com.example.shopping.Entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String role;
    private String address;
    private String profilePictureUrl;
    // Default constructor (required by JPA)
    public Users() {}

    // Parameterized constructor
    public Users(String username, String email, String password, String phone, String gender, String role, String address, String profilePictureUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
        this.address = address;
        this.profilePictureUrl = profilePictureUrl;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// Getters and Setters (optional for encapsulation)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", phone="
				+ phone + ", gender=" + gender + ", role=" + role + ", address=" + address + ", profilePictureUrl="
				+ profilePictureUrl + ", purchasedProducts]";
	}

	
	
	
    
}
