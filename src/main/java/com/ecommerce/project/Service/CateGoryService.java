package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Payload.CategoryDto;
import com.ecommerce.project.Payload.CategoryResponse;

import java.util.List;


public interface CategoryService {
    CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize);
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto deleteCategory(Long categoryId);


    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
}
