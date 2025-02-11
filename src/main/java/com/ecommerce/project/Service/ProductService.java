package com.ecommerce.project.Service;


import com.ecommerce.project.Model.Product;
import com.ecommerce.project.Payload.ProductDto;
import com.ecommerce.project.Payload.ProductResponse;

public interface ProductService {
    ProductDto addProduct(Long categoryId, Product product);

    ProductResponse getProducts();

    ProductResponse searchByCategoryId(Long categoryId);

    ProductResponse searchByKeyword(String keyword);
}
