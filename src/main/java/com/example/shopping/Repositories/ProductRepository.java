package com.example.shopping.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.shopping.Entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p WHERE " +
	           "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
	           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
	           "LOWER(p.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
	           "LOWER(p.color) LIKE LOWER(CONCAT('%', :query, '%'))")
	    List<Product> searchProducts(@Param("query") String query);

	Optional<Product> findById(long id);

	Optional<Product> getProductById(Long productId);




	



	
	}

    // No additional methods needed for basic CRUD functionality


