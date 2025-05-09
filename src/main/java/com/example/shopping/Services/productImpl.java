package com.example.shopping.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.shopping.Entities.Product;
import com.example.shopping.Repositories.ProductRepository;

@Service
public class productImpl implements productService {
	@Autowired
	ProductRepository prepo;

	@Override
	public void addProduct(Product product) {
		prepo.save(product);
	
		
	}
	public List<Product> getAllProducts() {
        return prepo.findAll();
    }

    // Get product by ID
    @Override
    public Optional<Product> getProductById(Long id) {
        return prepo.findById(id);
    }

    @Override
    public Product updateProduct(Long id, String name, String brand, double price, double discount, int quantity,
                                 String color, String description, String category, MultipartFile imageFile) {
        Optional<Product> existingProduct = prepo.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(name);
            product.setBrand(brand);
            product.setPrice(price);
            product.setDiscount(discount);
            product.setQuantity(quantity);
            product.setColor(color);
            product.setDescription(description);
            product.setCategory(category);

            if (imageFile != null && !imageFile.isEmpty()) {
                // Save image to file system and set URL
                String filename = imageFile.getOriginalFilename();
                String path = "path/to/images/" + filename; // set this to your image folder
                try {
                    imageFile.transferTo(new File(path));
                    product.setImageUrl("/images/" + filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return prepo.save(product);
        }
        return null;
    }


    public boolean deleteProduct(Long id) {
        Optional<Product> product = prepo.findById(id);
        if (product.isPresent()) {
            prepo.deleteById(id);
            return true;
        }
        return false;
    }
	@Override
	public List<Product> searchProducts(String query) {
		String[] keywords = query.split(" "); // Split input into keywords

        // Fetch all products and filter by keywords
        return prepo.findAll().stream()
            .filter(product -> Arrays.stream(keywords)
                .anyMatch(keyword -> product.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                     product.getBrand().toLowerCase().contains(keyword.toLowerCase()) ||
                                     product.getCategory().toLowerCase().contains(keyword.toLowerCase()) ||
                                     product.getColor().toLowerCase().contains(keyword.toLowerCase())))
            .collect(Collectors.toList()); // Collect matching products into a list
    }
    
	public List<Product> filterProducts(String category, String brand, Double minPrice, Double maxPrice) {
        List<Product> products = prepo.findAll();

        return products.stream()
                .filter(product -> category == null || product.getCategory().equalsIgnoreCase(category))
                .filter(product -> brand == null || product.getBrand().toLowerCase().contains(brand.toLowerCase()))
                .filter(product -> minPrice == null || product.getPrice() >= minPrice)
                .filter(product -> maxPrice == null || product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
	public Product findProductById(Long id) {
        return prepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }
	
	
	
}
