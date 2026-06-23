package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    // 1. Get all categories
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    // 2. Get a category by its ID
    public Category getById(int categoryId)
    {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElse(null); // Returns the Category if found, or null if it doesn't exist
    }

    // 3. Create and save a new category
    public Category create(Category category)
    {
        return categoryRepository.save(category);
    }

    // 4. Update an existing category
    public Category update(int categoryId, Category category)
    {
        // Check if the category exists in the database first
        if (categoryRepository.existsById(categoryId)) {
            // Ensure the entity object uses the correct ID matching the URL path
            // (Assumes your Category model has a setCategoryId() or similar setter method)
            category.setCategoryId(categoryId);
            return categoryRepository.save(category); // save() acts as an update if the ID already exists
        }
        return null;
    }

    // 5. Delete a category by its ID
    public void delete(int categoryId)
    {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        }
    }
}