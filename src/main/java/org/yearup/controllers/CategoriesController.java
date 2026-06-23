package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

// 1. Mark as a REST Controller, define base URL route, and enable Cross-Origin Requests
@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoriesController
{
    private final CategoryService categoryService;
    private final ProductService productService;

    // 2. Autowired constructor dependency injection
    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService)
    {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // 3. GET all categories
    @GetMapping
    public List<Category> getAll()
    {
        return categoryService.getAllCategories();
    }

    // 4. GET a single category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id)
    {
        Category category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity.notFound().build(); // Returns 404 if category doesn't exist
        }
        return ResponseEntity.ok(category); // Returns 200 OK with the object
    }

    // 5. GET all products matching a specific category ID
    @GetMapping("/{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // Change getProductsByCategoryId to listByCategoryId
        return productService.listByCategoryId(categoryId);
    }

    // 6. POST a new category (ADMIN Only)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        Category createdCategory = categoryService.create(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED); // Returns 201 Created
    }

    // 7. PUT to update a category (ADMIN Only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        categoryService.update(id, category);
        return ResponseEntity.ok().build(); // Returns 200 OK
    }

    // 8. DELETE a category (ADMIN Only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        categoryService.delete(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
}
