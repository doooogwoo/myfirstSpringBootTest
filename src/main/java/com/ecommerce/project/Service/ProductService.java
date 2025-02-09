package com.ecommerce.project.Service;


import com.ecommerce.project.Model.Product;
import com.ecommerce.project.Payload.ProductDto;

public interface ProductService {
    ProductDto addProduct(Long categoryId, Product product);
}
