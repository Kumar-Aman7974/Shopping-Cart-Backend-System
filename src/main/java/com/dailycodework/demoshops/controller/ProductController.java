package com.dailycodework.demoshops.controller;


import com.dailycodework.demoshops.dto.ProductDto;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Product;
import com.dailycodework.demoshops.request.AddProductRequest;
import com.dailycodework.demoshops.request.ProductUpdateRequest;
import com.dailycodework.demoshops.response.ApiResponse;
import com.dailycodework.demoshops.service.product.IProductService;
import com.dailycodework.demoshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.IconView;
import java.util.EventListenerProxy;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {

        List<Product> products = productService.getAllProducts();

        List<ProductDto> convertedProducts = productService.getConvertedProducts(products); // here we are adding

        return  ResponseEntity.ok(new ApiResponse("success", convertedProducts)); // change from products to convertedProducts

    }
//    @GetMapping("/all")
//    public ResponseEntity<ApiResponse> getAllProducts() {
//
//        List<Product> products = productService.getAllProducts();
//
//        return  ResponseEntity.ok(new ApiResponse("success", products));
//
//    }


    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable  Long productId)
    {
        try {


            Product product = productService.getProductById(productId); // when we are using @PathVariable make sure you path productId and method parameter id both are same;

            var productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success", productDto));

        }
        catch (ResourceNotFoundException e) {

            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product)
    {

        try {
            Product theProduct = productService.addProduct(product);
            ProductDto productDto  = productService.convertToDto(theProduct);
//Which HTTP status should be used when creating a resource?
//
//Correct answer:
//
//201 CREATED
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Add product success!", productDto));

        }
        catch (Exception e) {

            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));

        }
    }


    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse>  updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId)

    {
        try{

            Product theProduct = productService.updateProduct(request, productId);
            ProductDto productDto = productService.convertToDto(theProduct);
            return  ResponseEntity.ok(new ApiResponse("Update product success!", productDto));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }



    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)
    {

        try {
        productService.deleteProductById(productId);
        return ResponseEntity.ok(new ApiResponse("delete success!", productId));
    }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

        }


        @GetMapping("/products/by/brand-and-name")//         here we are changed @PathVariables to @RequestParam why?
        public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
            try {
                List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                if (products.isEmpty()) {
                    return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ", null));
                }
                return ResponseEntity.ok(new ApiResponse("success", convertedProducts));


            }
            catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

            }
    }



    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));


        }
        catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));

        }
    }

    @GetMapping("/products/{name}/products")
    public  ResponseEntity<ApiResponse> getProductByName(@PathVariable String name)
    {
        try {
            List<Product> products = productService.getProductsByName(name);

            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NO products found", null));

            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }


    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse>  findProductByBrand(@RequestParam String brand)
    {
        try {
            List<Product> products = productService.getProductsByBrand(brand);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NO products found! " , null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));

        }
    }

    @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category)
    {
        try {
            List<Product> products = productService.getProductsByCategory(category);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found " , null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Product found ",convertedProducts));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));

        }
    }

    //  2:44

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name)
    {
        try {

            var productCount = productService.countProductByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!",  productCount));

        }
        catch (Exception e)
        {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));

        }
    }

}
// 3.6
