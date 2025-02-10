package com.ecommerce.project.Service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Model.Product;
import com.ecommerce.project.Payload.ProductDto;
import com.ecommerce.project.Payload.ProductResponse;
import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.Repository.ProductRepository;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId));
        product.setCategory(category);
        product.setImage("default png");
        double specialPrice = product.getPrice()-
                ((product.getDiscount() * 0.01)) * product.getPrice();
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductResponse getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }
}
