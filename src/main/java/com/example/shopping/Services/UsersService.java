package com.example.shopping.Services;

import java.util.Map;
import java.util.Optional;

import com.example.shopping.Entities.Product;
import com.example.shopping.Entities.Users;

public interface UsersService 
{
	public Users addUser(Users user);
	
	public boolean emailExists(String email);

	public boolean validateUser(String email, String password);

	public String getRole(String email);

	public boolean validatePhone(String phone, String password);

	public Map<String, String> getAccountDetailsByEmail(String email);

	public Users getUser(String email);

	public void updateUser(Users user);

	public void processForgotPassword(String email) throws Exception;

	
	

	
	

	
}
