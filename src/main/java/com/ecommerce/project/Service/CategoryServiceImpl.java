package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    //private Long nextId = 1L;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    //@Transactional
    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }


    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        categoryRepository.delete(category);
        return categoryId +":" + category.getCategoryName()+ " is delete successful";
    }


    @Override
    public Category updateCategory(Category category, Long categoryId) {
        //逐個資料尋找，有需要時再拋出異常
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category is not find"));
        //將類別儲存回資料庫中
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }


}
