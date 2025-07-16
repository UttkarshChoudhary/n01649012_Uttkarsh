package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {

    private final ProductRepository coffeeRepository;

    public ProductServices(ProductRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    // Add a new coffee item
    public Product addProduct(Product product) {
        return coffeeRepository.save(product);  // Save to DB
    }

    // Get all coffee items
    public List<Product> getAllProdcuts() {
        return coffeeRepository.findAll();  // Fetch from DB
    }

    // Find coffee by ID
    public Optional<Product> getProductById(Long id) {
        return coffeeRepository.findById(id);  // Fetch by ID
    }

    // Update coffee details by ID
    public Optional<Product> updateProduct(Long id, Product updatedCoffee) {
        return coffeeRepository.findById(id).map(existingCoffee -> {
            existingCoffee.setName(updatedCoffee.getName());
            existingCoffee.setPrice(updatedCoffee.getPrice());
            existingCoffee.setDescription(updatedCoffee.getDescription());
            existingCoffee.setStock(updatedCoffee.getStock());
            return coffeeRepository.save(existingCoffee);  // Save updates
        });
    }

    // Remove coffee by ID
    public void deleteProduct(Long id) {
        coffeeRepository.deleteById(id);  // Delete by ID
    }
}
