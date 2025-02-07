package com.ecommerce.project.Controller;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Payload.CategoryDto;
import com.ecommerce.project.Payload.CategoryResponse;
import com.ecommerce.project.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategory(){
        CategoryResponse categoryResponse = categoryService.getAllCategory();
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }


    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDto> createCateGory (@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savesCategoryDTO = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savesCategoryDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto ,
                                                 @PathVariable Long categoryId){
           CategoryDto savedCategoryDto = categoryService.updateCategory(categoryDto,categoryId);
           return new ResponseEntity<>(savedCategoryDto,HttpStatus.OK);
    }
}
