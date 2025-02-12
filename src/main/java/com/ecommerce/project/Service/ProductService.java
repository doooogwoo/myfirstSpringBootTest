package com.ecommerce.project.Service;


import com.ecommerce.project.Payload.ProductDto;
import com.ecommerce.project.Payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDto addProduct(Long categoryId, ProductDto productDto);

    ProductResponse getProducts();

    ProductResponse searchByCategoryId(Long categoryId);

    ProductResponse searchByKeyword(String keyword);

    ProductDto updateProduct(Long productId, ProductDto product);

    ProductDto delete(Long productId);

    ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;
}
