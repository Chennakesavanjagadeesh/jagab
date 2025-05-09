package com.example.shopping.Controllers;

import java.nio.file.Files;
import java.util.stream.Collectors;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.Entities.Orders;
import com.example.shopping.Entities.Product;
import com.example.shopping.Repositories.ProductRepository;
import com.example.shopping.Services.productService;

import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class productController {
	@Autowired
	productService pserv;
	@Autowired
	ProductRepository prepo;

	@PostMapping("/add-product")
	public ResponseEntity<String> addProduct(
	        @RequestParam("name") String name,
	        @RequestParam("brand") String brand,
	        @RequestParam("price") Double price,
	        @RequestParam(value = "discount", required = false, defaultValue = "0") Double discount,
	        @RequestParam("quantity") Integer quantity,
	        @RequestParam(value = "color", required = false) String color,
	        @RequestParam(value = "description", required = false) String description,
	        @RequestParam("category") String category,
	        @RequestParam("imageFile") MultipartFile imageFile) {

	    // Validate required fields
	    if (name == null || brand == null || price == null || quantity == null || category == null || imageFile.isEmpty()) {
	        return ResponseEntity.badRequest().body("Missing required fields!");
	    }

	    try {
	        // Generate file name for image
	        String extension = imageFile.getOriginalFilename().split("\\.")[1]; // Get file extension
	        String newFileName = name.replaceAll("\\s+", "_") + "." + extension; // Create a sanitized file name

	        // Save image to the server (you can customize this path)
	        String uploadDirectory = "uploads/"; // Path where images will be stored
	        Path imagePath = Paths.get(uploadDirectory + newFileName);
	        Files.createDirectories(imagePath.getParent()); // Create directory if it doesn't exist
	        Files.write(imagePath, imageFile.getBytes()); // Save image file to the server

	        // Set the relative path to the imageUrl field
	        String imageUrl = "/" + uploadDirectory + newFileName;

	        // Create and save the Product object
	        Product product = new Product(name, brand, price, discount, quantity, color, description, imageUrl, category);
	        pserv.addProduct(product);

	        return ResponseEntity.ok("Product added successfully!");
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the error for debugging
	        return ResponseEntity.status(500).body("Failed to add product.");
	    }
	}


	@GetMapping("/view-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = pserv.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get product by ID
	@GetMapping("/view-product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
	    Optional<Product> product = pserv.getProductById(id);
	    
	    if (product != null) {
	        return ResponseEntity.ok(product);
	    } else {
	        return ResponseEntity.status(404).body("Product not found!");
	    }
	}


	@PutMapping(value = "/update-product/{id}", consumes = "multipart/form-data")
	public ResponseEntity<?> updateProduct(
	        @PathVariable Long id,
	        @RequestParam("name") String name,
	        @RequestParam("brand") String brand,
	        @RequestParam("price") double price,
	        @RequestParam("discount") double discount,
	        @RequestParam("quantity") int quantity,
	        @RequestParam("color") String color,
	        @RequestParam("description") String description,
	        @RequestParam("category") String category,
	        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

	    // Handle saving imageFile (if present) and update product
	    Product updated = pserv.updateProduct(id, name, brand, price, discount, quantity, color, description, category, imageFile);
	    return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.status(404).body("Product not found!");
	}


    // Delete product by ID
	@DeleteMapping("/delete-product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
	    boolean isDeleted = pserv.deleteProduct(id);
	    if (isDeleted) {
	        return ResponseEntity.ok("Product deleted successfully!");
	    } else {
	        return ResponseEntity.status(404).body("Product not found!");
	    }
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Map<String, Object>>> searchProducts(@RequestParam String query) {
	    List<Product> products = pserv.searchProducts(query);
	    List<Map<String, Object>> productResponses = products.stream().map(product -> {
	        Map<String, Object> response = new HashMap<>();
	        response.put("id", product.getId());
	        response.put("name", product.getName());
	        response.put("brand", product.getBrand());
	        response.put("color", product.getColor());
	        response.put("price", product.getPrice());
	        response.put("discount", product.getDiscount());
	        response.put("discountedPrice", product.getDiscountedPrice()); // Include calculated discounted price
	        response.put("quantity", product.getQuantity());
	        response.put("description", product.getDescription());
	        response.put("imageUrl", product.getImageUrl());
	        return response;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(productResponses);
	}
	@PutMapping("/products/{id}/update-quantity")
	public ResponseEntity<String> updateProductQuantity(@PathVariable Long id, @RequestParam int purchaseQuantity) {
	    Optional<Product> productOptional = pserv.getProductById(id); // Fetch product using Optional

	    // Check if product exists
	    if (productOptional.isEmpty()) {
	        return ResponseEntity.badRequest().body("Product not found.");
	    }

	    Product product = productOptional.get(); // Retrieve the product

	    // Check if sufficient stock is available
	    if (product.getQuantity() < purchaseQuantity) {
	        return ResponseEntity.badRequest().body("Not enough stock available.");
	    }

	    // Deduct the purchased quantity and save the updated product
	    product.setQuantity(product.getQuantity() - purchaseQuantity);
	    prepo.save(product);

	    // Return success response
	    String responseMessage = product.getQuantity() > 0
	            ? "Quantity updated successfully."
	            : "Product is now Out of Stock.";
	    return ResponseEntity.ok(responseMessage);
	}
	@GetMapping("/filter")
    public List<Product> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return pserv.filterProducts(category, brand, minPrice, maxPrice);
    }
	

	@GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(pserv.findProductById(productId));
    }
	

}





