package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.services.ProductServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductServices coffeeService;

    public ProductController(ProductServices coffeeService) {
        this.coffeeService = coffeeService;
    }

    // DISPLAY ALL COFFEES (LIST PAGE)
    @GetMapping
    public String getAllCoffees(Model model) {
        model.addAttribute("product", coffeeService.getAllProdcuts());
        return "coffee-list";  // Return view (coffee-list.html)
    }

    // DISPLAY FORM TO ADD A NEW COFFEE
    @GetMapping("/add")
    public String showAddCoffeeForm(Model model) {
        model.addAttribute("product", new Product());
        return "coffee-add";  // Return view (coffee-add.html)
    }

    // ADD A NEW COFFEE (FORM SUBMISSION)
    @PostMapping("/add")
    public String addCoffee(@ModelAttribute Product coffee) {
        coffeeService.addProduct(coffee);
        return "redirect:/products";  // Redirect to list after adding
    }

    // VIEW DETAILS OF A COFFEE
    @GetMapping("/{id}")
    public String getCoffeeDetails(@PathVariable Long id, Model model) {
        coffeeService.getProductById(id).ifPresent(coffee -> model.addAttribute("coffee", coffee));
        return "coffee-details";  // Return view (coffee-details.html)
    }

    // SHOW UPDATE FORM
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Product> coffeeOptional = coffeeService.getProductById(id);
        if (coffeeOptional.isPresent()) {
            model.addAttribute("product", coffeeOptional.get());
            return "coffee-edit";  // Return edit view
        } else {
            return "redirect:/products";  // Redirect if coffee not found
        }
    }

    // HANDLE UPDATE FORM SUBMISSION USING PUT
    @PutMapping("/update/{id}")
    public String updateCoffee(@PathVariable Long id, @ModelAttribute Product coffee) {
        coffeeService.updateProduct(id, coffee);
        return "redirect:/products";  // Redirect to the coffee list after updating
    }

    // DELETE A COFFEE ITEM
    @GetMapping("/delete/{id}")
    public String deleteCoffee(@PathVariable Long id) {
        coffeeService.deleteProduct(id);
        return "redirect:/products";  // Redirect to list after deletion
    }
}
