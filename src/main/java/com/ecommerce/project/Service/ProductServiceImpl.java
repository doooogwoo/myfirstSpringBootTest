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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private  String path;
    @Override
    public ProductDto addProduct(Long categoryId, ProductDto productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId));
        Product product = modelMapper.map(productDto,Product.class);
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
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        //Get the existing product from DB
        Product productFindFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","ProductId",productId));
        Product product = modelMapper.map(productDto,Product.class);
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

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
        //Get the product from DB
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() ->new ResourceNotFoundException("Product","productId",productId));
        //Upload image to server

        //Get the file name of uploaded image
        //Updating the new file name to the product
        //String path = "images/";--->@Value
        String fileName = fileService.uploadImage(path,image);
        productFromDB.setImage(fileName);

        //save updated product
        Product updatedProduct = productRepository.save(productFromDB);
        //return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct,ProductDto.class);
    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        //File names of current / original file
        //這一行從上傳的檔案中獲取檔案的原始名稱。
        // 例如，如果使用者上傳了一個名為image.jpg的檔案，originalFilName將會是"image.jpg"。
        String originalFilName = file.getOriginalFilename();

        //Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        //mat.jpg --> 7578 -->7578.jpg
        //它從originalFilName中提取了副檔名（例如.jpg），然後將這個副檔名附加到隨機生成的ID後面。
        // 例如，假設隨機ID是7578，原始檔案是image.jpg，那麼fileName就會是7578.jpg。
        String fileName = randomId.concat(originalFilName.substring(originalFilName.lastIndexOf('.')));

        //這行用來檢查指定的path（存放檔案的目錄）是否存在。new File(path)會創建一個指向該目錄的File物件，但不會實際創建該目錄。
        //String filePath = path + File.pathSeparator + fileName;
        String filePath = path + File.separator + fileName;

        //Check if path exist and create
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();
        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }


}