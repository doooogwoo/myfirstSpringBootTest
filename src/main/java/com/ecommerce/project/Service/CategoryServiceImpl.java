package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        categories.remove(category);
        return categoryId + " is delete successful";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(category1 -> category1.getCategoryId().equals(categoryId))
                .findFirst();
        if (optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
        }
    }


}
