package com.dailycodework.demoshops.service.product;

import com.dailycodework.demoshops.dto.ProductDto;
import com.dailycodework.demoshops.model.Product;
import com.dailycodework.demoshops.request.AddProductRequest;
import com.dailycodework.demoshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

   Product addProduct(AddProductRequest request);

    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);

    List<Product> getAllProducts();
    List<Product>  getProductsByCategory(String category);
    List<Product>  getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);
    Long  countProductByBrandAndName(String brand, String name);


 List<ProductDto> getConvertedProducts(List<Product> products);

 ProductDto convertToDto(Product product);
}
