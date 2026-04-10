package com.dailycodework.demoshops.service.product;

import com.dailycodework.demoshops.dto.ImageDto;
import com.dailycodework.demoshops.dto.ProductDto;
import com.dailycodework.demoshops.exceptions.ProductNotFoundException;
import com.dailycodework.demoshops.model.Category;
import com.dailycodework.demoshops.model.Image;
import com.dailycodework.demoshops.model.Product;
import com.dailycodework.demoshops.repository.CategoryRepository;
import com.dailycodework.demoshops.repository.ImageRepository;
import com.dailycodework.demoshops.repository.ProductRepository;
import com.dailycodework.demoshops.request.AddProductRequest;
import com.dailycodework.demoshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // tell me what's the role of this annotation and how it diff from another constructor annotations;
public class ProductService implements IProductService {

private final ProductRepository productRepository;

private final CategoryRepository categoryRepository;

private final ImageRepository imageRepository;

// for mapper
private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) { // instead of using the Product you can use this addProductRequest
       // check if the category is found in the DB
        // if Yes, set it as the new product category
        // if No, the save it as a new category
        // then set as the new product category;

        Category category = Optional
                .ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {

                    Category newCategory =
                            new Category(request.getCategory().getName());

                    return categoryRepository.save(newCategory);

                });

        //Category category = categoryRepository.findByName(request.getCategory().getName());

        System.out.println(category);
        request.setCategory(category);
        return  productRepository.save(createProduct(request, category));

    }

    private Product createProduct(AddProductRequest request, Category category) {

        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }


    @Override
    public Product getProductById(Long id) {
//        return productRepository.findById(id)//orElseThrow( () -> new ProductNotFoundException("Product not found!"));
//                // here we are customized this error from null to this using this customizer;
//                .ifPresentOrElse(productRepository::delete,
//                        () -> {throw new ProductNotFoundException("Product not found!");
//                }

        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("product not found "+id));
    }

    @Override
    public void deleteProductById(Long id) {

        // BIg picture here , so explain how this work and internally how it's create another code explain ,is this method reference
        // and then how to write this in normal code and then lambda code and then method reference. how both are connects
        productRepository.findById(id).ifPresent(productRepository:: delete);


    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

        return  productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow( () -> new ProductNotFoundException("Product not found!"));

    }

   private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
       existingProduct.setBrand(request.getBrand());
       existingProduct.setPrice(request.getPrice());
       existingProduct.setInventory(request.getInventory());
       existingProduct.setDescription(request.getDescription());

       Category category = categoryRepository.findByName(request.getCategory().getName());
       existingProduct.setCategory(category);

       return existingProduct;


   }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override// why we are using the List , here we are not returning all data ,only returning the brand name which is single like get id
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products)
    {
        return  products.stream().map(this::convertToDto).toList();
    }


    @Override
    public ProductDto convertToDto(Product product)
    {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
        .map(image -> modelMapper.map(image, ImageDto.class))
        .toList();

        productDto.setImages(imageDtos);
        return productDto;


    }

}
