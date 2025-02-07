package com.ecommerce.project.Controller;

import com.ecommerce.project.Payload.CategoryDto;
import com.ecommerce.project.Payload.CategoryResponse;
import com.ecommerce.project.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/echo")                       //要搜索的話可以依靠這個關鍵字
    public ResponseEntity<String> echoMessage(@RequestParam (name = "message",required = false) String message){
    //public ResponseEntity<String> echoMessage(@RequestParam (name = "message",defaultValue = "Hello World!") String message)
        return new ResponseEntity<>("Echo Message: " + message,HttpStatus.OK);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategory(){
        CategoryResponse categoryResponse = categoryService.getAllCategory();
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }


    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDto> createCateGory (@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savesCategoryDTO = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savesCategoryDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long categoryId){
            CategoryDto deleteCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deleteCategory,HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto ,
                                                 @PathVariable Long categoryId){
           CategoryDto savedCategoryDto = categoryService.updateCategory(categoryDto,categoryId);
           return new ResponseEntity<>(savedCategoryDto,HttpStatus.OK);
    }
}
