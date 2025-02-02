package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CateGoryService{
    private List<Category> categories = new ArrayList<>();
    @Override
    public List<Category> getAllCategory() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter(category1 -> category1.getCategoryId().equals(categoryId))
                .findFirst().orElse(null);
        if (category == null) return "Category not found.";
        categories.remove(category);
        return categoryId + " is delete successful";
    }
}
