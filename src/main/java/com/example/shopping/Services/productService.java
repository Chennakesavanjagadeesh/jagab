package com.example.shopping.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.shopping.Entities.Product;

public interface productService {
 public void addProduct(Product product);
public boolean deleteProduct(Long id);

public List<Product>  getAllProducts();

public List<Product> searchProducts(String query);
 public Optional<Product> getProductById(Long id);

public  List<Product> filterProducts(String category, String brand, Double minPrice, Double maxPrice);
	// TODO Auto-generated method stub
public Product findProductById(Long id);
public Product updateProduct(Long id, String name, String brand, double price, double discount, int quantity,
                             String color, String description, String category, MultipartFile imageFile);






 
}
