package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CateGoryService {
    List<Category> getAllCategory();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);
}
