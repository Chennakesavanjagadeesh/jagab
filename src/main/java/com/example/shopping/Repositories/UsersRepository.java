package com.example.shopping.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.Entities.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>  {
	
	
	public Users findByEmail(String email);

	public Users findByPhone(String phone);

	public Optional<Users> findById(Long userId);
}