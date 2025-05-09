package com.example.shopping.Services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.Entities.Product;
import com.example.shopping.Entities.Users;
import com.example.shopping.Repositories.ProductRepository;
import com.example.shopping.Repositories.UsersRepository;
@Service
public class UsersServiceImplementation
implements UsersService
{
	@Autowired
	UsersRepository repo;
	@Autowired
	ProductRepository prepo;
	
	@Override
	public Users addUser(Users user) 
	{
		return repo.save(user);
		
	}

	@Override
	public boolean emailExists(String email) {
		
		if(repo.findByEmail(email) == null) 
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public boolean validateUser(String email, String password) {
		
		Users user = repo.findByEmail(email);
		String db_password = user.getPassword();
		if(db_password.equals(password))
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}

	@Override
	public String getRole(String email) {
		return (repo.findByEmail(email).getRole());
	}

	@Override
	public boolean validatePhone(String phone, String password) {
		Users user=repo.findByPhone(phone);
		String g_pass=user.getPassword();
		if(g_pass.equals(password))
		{
			return true;
		}
		else
		{
		return false;
		}
	}
	
	public Map<String, String> getAccountDetailsByEmail(String email) {
        // Validate email
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        // Fetch user from the database
        Users user = repo.findByEmail(email);
        if (user != null) {
            // Convert user entity to a map of account details
            return Map.of(
                "name", user.getUsername(),
                "email", user.getEmail(),
                "phone", user.getPhone(),
                "gender", user.getGender()
            );
        }

        // Return null if no user is found
        return null;
    }

	@Override
	public Users getUser(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		repo.save(user);
		
	}
	public void processForgotPassword(String email) throws Exception {
	    Users user = repo.findByEmail(email);
	    if (user == null) {
	        throw new Exception("No user found with the provided email.");
	    }

	    // Update password directly in the database (as per user request)
	    user.setPassword("newSecurePassword123"); // In production, hash the password
	    repo.save(user);
	}

	           
	}

	

	

