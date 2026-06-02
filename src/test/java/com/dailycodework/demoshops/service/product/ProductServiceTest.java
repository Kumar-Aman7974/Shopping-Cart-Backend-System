package com.dailycodework.demoshops.service.product;


import com.dailycodework.demoshops.exceptions.AlreadyExistsException;
import com.dailycodework.demoshops.exceptions.ProductNotFoundException;
import com.dailycodework.demoshops.model.Category;
import com.dailycodework.demoshops.model.Product;
import com.dailycodework.demoshops.repository.CategoryRepository;
import com.dailycodework.demoshops.repository.ImageRepository;
import com.dailycodework.demoshops.repository.ProductRepository;
import com.dailycodework.demoshops.request.AddProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//step 1 @ExtendWith
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    //step 2 Mock the dependencies that ProductService needs
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ModelMapper modelMapper;

    // step 3: Create the real ProductService productService;
    @InjectMocks
    private ProductService productService;

    private AddProductRequest addProductRequest;

    private Product testProduct;


    @BeforeEach
    void setUp() {
        // Create a dummy product for our tests using your custom constructor

        Category category = new Category("Electronics");
        testProduct = new Product(
                "MacBook Pro",
                "Apple",
                BigDecimal.valueOf(2499.99),
                10,
                "Fast laptop",
                null
        );
        // since our entity doesn't have an Id in the constructor,
        // we use Spring's ReflectionTestUtils to simulate the DB generated ID
        // ReflectionTestUtils.setField(testProduct, "id, 1L);
        ReflectionTestUtils.setField(testProduct, "id", 1L);

        // 2. INITIALIZE THE REQUEST OBJECT HERE!
        // Use whatever constructor or setters your AddProductRequest class has.
        // I am guessing it looks something like this based on your service code:
        addProductRequest = new AddProductRequest(
                "MacBook Pro",
                "Apple",
                BigDecimal.valueOf(2499.99),
                10,
                "Fast laptop",
                category // assuming you have a nested DTO
        );

    }

    @Nested
    @DisplayName("Test for getProductById")
    class GetProductByIdTests {

        @Test
        @DisplayName("Should return product when ID exists")
        void getProductBy_ExistingId_ReturnsProduct() {
            // given
            Long productId = 1L;
            when(productRepository.findById(productId))
                    .thenReturn(Optional.of(testProduct));

            // when
            // Call the real method
            Product result = productService.getProductById(productId);


            // ASSERT: CHeck the result
            assertNotNull(result);

            assertEquals("MacBook Pro", result.getName());

            assertEquals(BigDecimal.valueOf(2499.99), result.getPrice());


            // VERIFY : Make sure the repository was actually called
            verify(productRepository).findById(productId);
        }

        @Test
        @DisplayName("Should throw ProductNotFoundException when ID does not exist")
        void getProductById_NonExistingId_ThrowsException() {
            // ARRANGE
            Long wrongId = 999L;

            when(productRepository.findById(wrongId))
                    .thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(ProductNotFoundException.class,
                    () -> productService.getProductById(wrongId)
            );

            //VERIFY: Ensure we only called find, never save
            verify(productRepository, never()).save(any());
        }


    }

    // Test for " getProductsByName(String name)

    @Nested
    @DisplayName("Test for getProductByName")
    class GetProductByNameTests {

        @Test
        @DisplayName("Should return list of products when name matches")
        void getProductByName_ExistingName_ReturnList() {
            // ARRANGE
            String name = "MacBook Pro";

            // create a second product to make the list realistic
            Product secondProduct = new Product(
                    "MacBook Pro", "Apple", BigDecimal.valueOf(1919.99), 5, "Base model",
                    null);

            ReflectionTestUtils.setField(secondProduct, "id", 2L);

            List<Product> mockProducts = List.of(testProduct, secondProduct);

            when(productRepository.findByName(name))
                    .thenReturn(mockProducts);

            // ACT
            List<Product> result = productService.getProductsByName(name);

            // ASSERT
            assertThat(result)
                    .isNotNull()
                    .hasSize(2)
                    .extracting(Product::getName)
                    .containsExactly("MacBook Pro", "MacBook Pro");

            // VERIFY
            verify(productRepository).findByName(name);

        }

        @Test
        @DisplayName("Should return empty list when no products match name")
        void getProductsByName_NonExistingName_ReturnEmptyList() {

            // ARRANGE
            String name = "Window Laptop";
            when(productRepository.findByName(name))
                    .thenReturn(List.of()); // return empty list


            // ACT
            List<Product> result = productService.getProductsByName(name);

            //ASSERT
            assertThat(result).isNotNull().isEmpty();

            //VERIFY
            verify(productRepository).findByName(name);
        }
    }


    @Nested
    @DisplayName("Test for add Product")
    public class AddProductTest {
// Case 1: Product Already Exists

        @Test
        @DisplayName("Should throw AlreadyExistsException if product name + brand exists")
        void addProduct_ExistingProduct_ThrowsException() {
            //ARRANGE: Mock that the product already exists in DB
            when(productRepository.existsByNameAndBrand("MacBook Pro", "Apple"))
                    .thenReturn(true);

            // ACT & ASSERT
            AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () ->
                    {
                        productService.addProduct(addProductRequest);

                    });
                        // Verify the exception message
                        assertTrue(exception.getMessage().contains("Apple MacBook Pro already exists"));


            // VERIFY: Ensure we never tried to save anything
            verify(productRepository, never()).save(any());
            verify(categoryRepository, never()).save(any());
        }

        // CASE 2: Product is New, but Category ALREADY Exists in DB
        @Test
        @DisplayName("Should use existing category if found in DB")
        void addProduct_New_Product_Existing_SavesProduct() {
            //ARRANGE: Product doesn't exist yet
            when(productRepository.existsByNameAndBrand("MacBook Pro","Apple"))
                    .thenReturn(false);

            // 2. Category Electronics already exists in DB
            Category existingCategory = new Category("Electronics");
            ReflectionTestUtils.setField(existingCategory, "id", 10L);//Simulate DB ID

            when(categoryRepository.findByName("Electronics"))
                    .thenReturn(existingCategory);

            // 3. Mock the product save to return the product with an ID
            when(productRepository.save(any(Product.class)))
                    .thenAnswer(invocation -> {
                        Product productToSave = invocation.getArgument(0);
                        ReflectionTestUtils.setField(productToSave, "id", 100L);//Simulate DB auto-generating ID

                        return productToSave;
                    });

            // ACT
            Product savedProduct = productService.addProduct(addProductRequest);

            // ASSERT
            assertNotNull(savedProduct);
            assertEquals(100L, savedProduct.getId());
            assertEquals("MacBook Pro", savedProduct.getName());

            //CRITICAL: Verify it used the existing category
            assertNotNull(savedProduct.getCategory());
            assertEquals(10L, savedProduct.getCategory().getId());
            assertEquals("Electronics", savedProduct.getCategory().getName());


            // VERIFY: Category save should NEVER be called because it already existed!
            verify(categoryRepository, never()).save(any());
            verify(productRepository, times(1)).save(any(Product.class));


        }

        // CASE 3: Product is New, AND Category DOES NOT Exist in DB
        @Test
        @DisplayName(" Should create and save new category if not found in DB")
        void addProduct_NewProduct_NewCategory_SavesBoth() {
            // ARRANGE
            when(productRepository.existsByNameAndBrand("MacBook Pro","Apple"))
                    .thenReturn(false);

            when(categoryRepository.findByName("Electronics"))
                    .thenReturn(null);

            when(categoryRepository.save(any(Category.class)))
                    .thenAnswer(invocation -> {
                        Category categoryToSave = invocation.getArgument(0);
                        ReflectionTestUtils.setField(categoryToSave, "id", 20L);
                        return categoryToSave;
                    });

            when(productRepository.save(any(Product.class)))
                    .thenAnswer(invocation -> {
                        Product productToSave = invocation.getArgument(0);
                        ReflectionTestUtils.setField(productToSave, "id", 200L);
                        return productToSave;
                    });

            // ACT
            Product savedProduct = productService.addProduct(addProductRequest);

            // ASSERT (JUnit 5 style)
            assertNotNull(savedProduct);
            assertEquals(200L, savedProduct.getId());

            assertNotNull(savedProduct.getCategory());
            assertEquals(20L, savedProduct.getCategory().getId());
            assertEquals("Electronics", savedProduct.getCategory().getName());

            // VERIFY
            verify(categoryRepository, times(1)).save(any(Category.class));
            verify(productRepository, times(1)).save(any(Product.class));
        }

    }

}