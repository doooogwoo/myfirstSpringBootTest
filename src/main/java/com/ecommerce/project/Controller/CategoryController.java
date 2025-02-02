package com.ecommerce.project.Controller;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Service.CateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CateGoryService cateGoryService;
    @GetMapping("/api/public/categories")
    public List<Category> getAllCategory(){
        return cateGoryService.getAllCategory();
    }


    @PostMapping("/api/public/categories")
    public String CateGory (@RequestBody Category category){
        cateGoryService.createCategory(category);
        return "CateGory added successful";
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId){
        String status = cateGoryService.deleteCategory(categoryId);
        return status;
    }
}
