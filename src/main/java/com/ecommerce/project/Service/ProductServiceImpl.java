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
        product.setDescription(product.getDescription());
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

    @Override
    public ProductResponse searchByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","CategoryId",categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword +'%');
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;

    }

    @Override
    public ProductDto updateProduct(Long productId, Product product) {
        //Get the existing product from DB
        Product productFindFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","ProductId",productId));
        //update the product info with user shared
        productFindFromDB.setProductName(product.getProductName());
        productFindFromDB.setDescription(product.getDescription());
        productFindFromDB.setQuantity(product.getQuantity());
        productFindFromDB.setDiscount(product.getDiscount());
        productFindFromDB.setPrice(product.getPrice());
        productFindFromDB.setSpecialPrice(product.getSpecialPrice());

        //save to database
        Product saveProduct = productRepository.save(productFindFromDB);

        return modelMapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->new ResourceNotFoundException("Product","productId",productId));
        productRepository.delete(product);
        return modelMapper.map(product,ProductDto.class);
    }
}
