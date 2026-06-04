# 🛒 Shopping Cart Backend System

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8.0-red.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/Kumar-Aman7974/Shopping-Cart-Backend-System)](https://github.com/Kumar-Aman7974/Shopping-Cart-Backend-System/stargazers)

## 📋 Table of Contents
- [About The Project](#-about-the-project)
- [Key Features](#-key-features)
- [Technology Stack](#-technology-stack)
- [System Architecture](#-system-architecture)
- [Getting Started](#-getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Configuration](#configuration)
    - [Running the Application](#running-the-application)
- [API Documentation](#-api-documentation)
    - [Product APIs](#product-apis)
    - [Cart APIs](#cart-apis)
    - [Order APIs](#order-apis)
    - [User APIs](#user-apis)
- [Database Schema](#-database-schema)
- [Project Structure](#-project-structure)
- [Code Examples](#-code-examples)
- [Testing](#-testing)
- [Error Handling](#-error-handling)
- [Security](#-security)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [Contact](#-contact)
- [Acknowledgments](#-acknowledgments)

## 📖 About The Project

**Shopping Cart Backend System** is a production-ready e-commerce backend solution built with **Spring Boot**. It provides a complete set of RESTful APIs for managing products, shopping carts, orders, and users. This project demonstrates best practices in software development including layered architecture, DTO pattern, exception handling, and clean code principles.

### Purpose
- ✅ Demonstrate expertise in **Java Spring Boot** development
- ✅ Showcase **RESTful API** design and implementation
- ✅ Implement **real-world e-commerce** functionality
- ✅ Follow **industry best practices** and design patterns

## ✨ Key Features

### Core Functionality
| Feature | Description | Status |
|---------|-------------|--------|
| **User Management** | Registration, authentication, profile management | ✅ |
| **Product Management** | CRUD operations with category support | ✅ |
| **Shopping Cart** | Add/remove items, update quantities | ✅ |
| **Order Processing** | Place orders, track order status | ✅ |
| **Image Upload** | Product image management | ✅ |
| **Category Management** | Product categorization | ✅ |

### Technical Features
- 🔄 **RESTful APIs** with proper HTTP methods and status codes
- 📦 **DTO Pattern** for data transfer between layers
- 🗄️ **Spring Data JPA** for database operations
- 🚨 **Global Exception Handling** with meaningful error messages
- 📝 **Structured Logging** for debugging and monitoring
- 🔌 **Layered Architecture** (Controller → Service → Repository)
- 💉 **Dependency Injection** using Spring IoC container
- 🧪 **Comprehensive Unit & Integration Testing** with JUnit 5 and Mockito
- 🔐 **JWT Token-Based Authentication** with secure token generation and validation
- 👥 **Role-Based Access Control (RBAC)** with @PreAuthorize annotations for endpoint protection

## 🛠️ Technology Stack

### Backend Technologies


### Detailed Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 LTS | Core programming language |
| **Spring Boot** | 3.3.5 | Application framework |
| **Spring Data JPA** | - | ORM and database operations |
| **Spring Security** | 6.x | Authentication and authorization |
| **Spring Web MVC** | - | REST API development |
| **Hibernate** | 6.x | JPA implementation |
| **Maven** | 3.8+ | Dependency management |
| **MySQL** | 8.0+ | Production database |
| **Lombok** | 1.18.x | Boilerplate code reduction |
| **ModelMapper** | 3.2.0 | Entity-DTO mapping |
| **JWT (JJWT)** | 0.12.6 | Token-based authentication |
| **JUnit 5** | 5.10.x | Unit testing framework |
| **Mockito** | 5.x | Mocking framework for tests |
| **AssertJ** | 3.25.x | Fluent assertion library |
| **Validation API** | 2.0+ | Input validation |

## 🏗️ System Architecture
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ Client │────▶│ Controller │────▶│ Service │
│ (Browser/ │ │ Layer │ │ Layer │
│ Mobile) │◀────│ (REST API) │◀────│ (Business) │
└──────────────┘ └──────────────┘ └──────────────┘
│
▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ Database │◀────│ Repository │◀────│ DTO │
│ (MySQL) │ │ Layer │ │ Layer │
└──────────────┘ └──────────────┘ └──────────────┘


## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

```bash
# Check Java version
java --version
# Should output: Java 21 or higher

# Check Maven version
mvn --version
# Should output: Maven 3.8 or higher

# Check MySQL (if using MySQL)
mysql --version

Installation
1. Clone the repository
git clone https://github.com/Kumar-Aman7974/Shopping-Cart-Backend-System.git
cd Shopping-Cart-Backend-System

2. Configure Database
Option A: Using MySQL (Production)
2. Configure Database
Option A: Using MySQL (Production)

Option B: Using H2 (Development)

No setup required, in-memory database

3. Configure application.properties
Update src/main/resources/application.properties:
# MySQL Configuration (uncomment for MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/shop_db
spring.datasource.username=shop_user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# H2 Configuration (uncomment for H2)
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.datasource.driverClassName=org.h2.Driver
# spring.datasource.username=sa
# spring.datasource.password=

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

4. Build the Project
# Clean and compile
mvn clean compile

# Package the application
mvn package

5. Run the Application
Using Maven:
mvn spring-boot:run

Using JAR file:
java -jar target/demo-shops-0.0.1-SNAPSHOT.jar

6. Verify Installation
Open your browser and navigate to:

API Base URL: http://localhost:8080/api

H2 Console (if using H2): http://localhost:8080/h2-console


📡 API Documentation
Base URL
http://localhost:8080/api

Authentication APIs
Method	Endpoint	Description	Request Body	Response
POST	/api/v1/auth/register	Register new user	User details	User object
POST	/api/v1/auth/login	User login	Email & password	JWT Token
GET	/api/v1/users/{id}	Get user profile	-	User details (requires auth)
PUT	/api/v1/users/{id}	Update user	Updated details	Updated user (requires auth)

**JWT Token Example:**
After login, the response will contain a JWT token:
```json
{
    "message": "Login successful",
    "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlkIjoxLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjAxNjM4MzAwLCJleHAiOjE2MDE2NDE5MDB9.signature"
}
```

Use this token in subsequent requests:
```bash
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
     http://localhost:8080/api/v1/products/all
```

Product APIs
Method	Endpoint	Description	Auth Required	Role Required
GET	/api/v1/products/all	Get all products	No	-
GET	/api/v1/products/product/{id}/product	Get product by ID	No	-
GET	/api/v1/products/category/{category}	Get products by category	No	-
POST	/api/v1/products/add	Create new product	Yes	ROLE_ADMIN
PUT	/api/v1/products/{id}	Update product	Yes	ROLE_ADMIN
DELETE	/api/v1/products/{id}	Delete product	Yes	ROLE_ADMIN

Example Request - Create Product:
POST /api/v1/products/add
Authorization: Bearer <JWT_TOKEN>

{
    "name": "iPhone 15 Pro",
    "brand": "Apple",
    "price": 999.99,
    "inventory": 50,
    "description": "Latest iPhone with A17 Pro chip",
    "category": {
        "name": "Electronics"
    }
}

Example Response:
{
    "id": 1,
    "name": "iPhone 15 Pro",
    "brand": "Apple",
    "price": 999.99,
    "inventory": 50,
    "description": "Latest iPhone with A17 Pro chip",
    "category": {
        "id": 1,
        "name": "Electronics"
    }
}

Cart APIs

Method	Endpoint	Description	Auth Required
GET	/api/v1/carts/{userId}	Get user's cart	Yes
POST	/api/v1/cartItems/add	Add item to cart	Yes
PUT	/api/v1/cartItems/update	Update cart item quantity	Yes
DELETE	/api/v1/cartItems/{cartItemId}	Remove from cart	Yes
DELETE	/api/v1/carts/{userId}/clear	Clear entire cart	Yes

Example Request - Add to Cart:
POST /api/v1/cartItems/add
Authorization: Bearer <JWT_TOKEN>

{
    "userId": 1,
    "productId": 1,
    "quantity": 2
}

Order APIs
Method	Endpoint	Description	Auth Required
POST	/api/v1/orders	Place order	Yes
GET	/api/v1/orders/{userId}	Get user orders	Yes
GET	/api/v1/orders/order/{orderId}	Get order by ID	Yes
PUT	/api/v1/orders/{orderId}/status	Update order status	Yes (ADMIN only)
DELETE	/api/v1/orders/{orderId}	Cancel order	Yes

Category APIs
Method	Endpoint	Description	Auth Required	Role Required
GET	/api/v1/categories	Get all categories	No	-
GET	/api/v1/categories/{id}	Get category by ID	No	-
POST	/api/v1/categories	Create category	Yes	ROLE_ADMIN
PUT	/api/v1/categories/{id}	Update category	Yes	ROLE_ADMIN
DELETE	/api/v1/categories/{id}	Delete category	Yes	ROLE_ADMIN

🗄️ Database Schema
Entity Relationship Diagram

**Authentication & User Management:**
```
┌──────────────┐         ┌──────────────┐         ┌──────────────┐
│     Role     │         │    User      │         │   Cart       │
├──────────────┤         ├──────────────┤         ├──────────────┤
│ id (PK)      │◀────────│ id (PK)      │────────▶│ id (PK)      │
│ name         │ (M2M)   │ firstName    │         │ user_id(FK)  │
└──────────────┘         │ lastName     │         │ totalAmount  │
                         │ email        │         └──────────────┘
                         │ password     │              │
                         │ createdAt    │              ▼
                         └──────────────┘    ┌──────────────────┐
                                            │    CartItem      │
                                            ├──────────────────┤
                                            │ id (PK)          │
                                            │ cart_id(FK)      │
                                            │ product_id(FK)   │
                                            │ quantity         │
                                            └──────────────────┘
```

**Order Management:**
```
┌──────────────────┐       ┌──────────────────┐       ┌──────────────┐
│      Order       │◀──────│    OrderItem     │──────▶│   Product    │
├──────────────────┤       ├──────────────────┤       ├──────────────┤
│ id (PK)          │       │ id (PK)          │       │ id (PK)      │
│ user_id(FK)      │       │ order_id(FK)     │       │ name         │
│ orderDate        │       │ product_id(FK)   │       │ brand        │
│ totalAmount      │       │ quantity         │       │ price        │
│ status           │       │ price            │       │ inventory    │
└──────────────────┘       └──────────────────┘       │ category_id  │
                                                      └──────────────┘
                                                             │
                                                             ▼
                                                      ┌──────────────┐
                                                      │   Category   │
                                                      ├──────────────┤
                                                      │ id (PK)      │
                                                      │ name         │
                                                      └──────────────┘
```

**Product Images:**
```
┌──────────────┐         ┌──────────────┐
│   Product    │────────▶│    Image     │
├──────────────┤(1:M)    ├──────────────┤
│ id (PK)      │         │ id (PK)      │
│ name         │         │ product_id   │
│ ...          │         │ image_url    │
└──────────────┘         └──────────────┘
```


📁 Project Structure
Shopping-Cart-Backend-System/
│
├── src/
│   ├── main/
│   │   ├── java/com/dailycodework/demoshops/
│   │   │   ├── DemoShopsApplication.java      # Main application class
│   │   │   │
│   │   │   ├── controller/                    # REST Controllers
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── CartController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── ImageController.java
│   │   │   │   └── UserController.java
│   │   │   │
│   │   │   ├── service/                       # Business Logic Layer
│   │   │   │   ├── product/
│   │   │   │   │   ├── IProductService.java
│   │   │   │   │   └── ProductService.java
│   │   │   │   ├── cart/
│   │   │   │   │   ├── ICartService.java
│   │   │   │   │   └── CartService.java
│   │   │   │   ├── order/
│   │   │   │   │   ├── IOrderService.java
│   │   │   │   │   └── OrderService.java
│   │   │   │   └── user/
│   │   │   │       ├── IUserService.java
│   │   │   │       └── UserService.java
│   │   │   │
│   │   │   ├── repository/                    # Data Access Layer
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── CartRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── CategoryRepository.java
│   │   │   │
│   │   │   ├── model/                         # Entity Classes
│   │   │   │   ├── Product.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Category.java
│   │   │   │   └── Image.java
│   │   │   │
│   │   │   ├── dto/                           # Data Transfer Objects
│   │   │   │   ├── ProductDto.java
│   │   │   │   ├── OrderDto.java
│   │   │   │   └── ImageDto.java
│   │   │   │
│   │   │   ├── request/                       # Request Objects
│   │   │   │   ├── AddProductRequest.java
│   │   │   │   └── ProductUpdateRequest.java
│   │   │   │
│   │   │   ├── response/                      # Response Objects
│   │   │   │   └── ApiResponse.java
│   │   │   │
| │   │   │   ├── exceptions/                    # Custom Exceptions
| │   │   │   │   ├── ResourceNotFoundException.java
| │   │   │   │   ├── ProductNotFoundException.java
| │   │   │   │   └── AlreadyExistsException.java
| │   │   │   │
| │   │   │   ├── security/                       # Security & JWT Configuration
| │   │   │   │   ├── config/
| │   │   │   │   │   └── ShopConfig.java         # Security beans, JWT config
| │   │   │   │   ├── Jwt/
| │   │   │   │   │   ├── JwtUtils.java           # JWT token generation & validation
| │   │   │   │   │   ├── AuthTokenFilter.java    # JWT request filter
| │   │   │   │   │   └── JwtAutEntryPoint.java   # JWT error handler
| │   │   │   │   ├── user/
| │   │   │   │   │   └── ShopUserDetails.java    # User principal for Spring Security
| │   │   │   │   └── service/
| │   │   │   │       └── ShopUserDetailsService.java  # User details service
| │   │   │   │
| │   │   │   ├── enums/                         # Enumerations
| │   │   │   │   └── OrderStatus.java
| │   │   │   │
| │   │   │   └── data/                          # Data Initialization
| │   │   │       └── DataInitializer.java       # Seed default roles and users
│   │   │
│   │   └── resources/
│   │       ├── application.properties        # Configuration
│   │       └── static/                       # Static resources
│   │
| │   └── test/                                  # Unit & Integration Tests
| │       └── java/com/dailycodework/demoshops/
| │           ├── service/
| │           │   ├── product/
| │           │   │   └── ProductServiceTest.java      # Service layer tests with Mockito
| │           │   └── cart/
| │           │       └── CartServiceTest.java         # Cart service tests
| │           └── DemoShopsApplicationTests.java       # Application context tests
│
├── pom.xml                                    # Maven configuration
├── mvnw                                       # Maven wrapper script
├── mvnw.cmd                                   # Maven wrapper (Windows)
└── README.md                                  # Project documentation

💻 Code Examples

Example 1: Creating a Product Service
@Service
@Transactional
public class ProductService implements IProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Product addProduct(AddProductRequest request) {
        // Check if product already exists
        if (productRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException("Product already exists!");
        }
        
        // Convert DTO to Entity
        Product product = new Product();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        
        // Save to database
        return productRepository.save(product);
    }
}

Example 2: REST Controller with Exception Handling
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private IProductService productService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("success", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("error", e.getMessage()));
        }
    }
}

| Example 3: Global Exception Handler
| @ControllerAdvice
| public class GlobalExceptionHandler {
|     
|     @ExceptionHandler(ResourceNotFoundException.class)
|     public ResponseEntity<ApiResponse> handleResourceNotFound(
|             ResourceNotFoundException ex) {
|         return ResponseEntity.status(HttpStatus.NOT_FOUND)
|             .body(new ApiResponse("error", ex.getMessage()));
|     }
|     
|     @ExceptionHandler(Exception.class)
|     public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
|         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
|             .body(new ApiResponse("error", "An unexpected error occurred"));
|     }
| }
| 
| Example 4: JWT Authentication & Role-Based Access Control
| @RestController
| @RequestMapping("${api.prefix}/products")
| public class ProductController {
|     
|     @PreAuthorize("hasRole('ROLE_ADMIN')")  // Only ADMIN can add products
|     @PostMapping("/add")
|     public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {
|         try {
|             Product theProduct = productService.addProduct(request);
|             ProductDto productDto = productService.convertToDto(theProduct);
|             return ResponseEntity.status(HttpStatus.CREATED)
|                 .body(new ApiResponse("Item added successfully!", productDto));
|         } catch (AlreadyExistsException e) {
|             return ResponseEntity.status(HttpStatus.CONFLICT)
|                 .body(new ApiResponse(e.getMessage(), null));
|         }
|     }
|     
|     @GetMapping("/all")  // Public endpoint
|     public ResponseEntity<ApiResponse> getAllProducts() {
|         List<Product> products = productService.getAllProducts();
|         List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
|         return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
|     }
| }
| 
| Example 5: User Authentication with JWT Token
| @RestController
| @RequestMapping("${api.prefix}/auth")
| public class AuthController {
|     
|     @PostMapping("/login")
|     public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
|         try {
|             Authentication authentication = authenticationManager.authenticate(
|                 new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
|             );
|             
|             String jwt = jwtUtils.generateTokenForUser(authentication);
|             return ResponseEntity.ok(new ApiResponse("Login successful", jwt));
|         } catch (AuthenticationException e) {
|             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
|                 .body(new ApiResponse("Invalid credentials", null));
|         }
|     }
| }
| 
| Example 6: Unit Testing with JUnit 5 & Mockito
| @ExtendWith(MockitoExtension.class)
| class ProductServiceTest {
|     
|     @Mock
|     private ProductRepository productRepository;
|     
|     @Mock
|     private CategoryRepository categoryRepository;
|     
|     @InjectMocks
|     private ProductService productService;
|     
|     @BeforeEach
|     void setUp() {
|         // Initialize test data
|     }
|     
|     @Test
|     @DisplayName("Should return product when ID exists")
|     void getProductById_ExistingId_ReturnsProduct() {
|         // Given
|         Long productId = 1L;
|         Product expectedProduct = new Product("iPhone", "Apple", BigDecimal.valueOf(999.99), 10, "Latest iPhone", null);
|         when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));
|         
|         // When
|         Product result = productService.getProductById(productId);
|         
|         // Then
|         assertThat(result).isNotNull();
|         assertThat(result.getName()).isEqualTo("iPhone");
|         verify(productRepository).findById(productId);
|     }
|     
|     @Test
|     @DisplayName("Should throw exception when product does not exist")
|     void getProductById_NonExistentId_ThrowsException() {
|         // Given
|         Long productId = 999L;
|         when(productRepository.findById(productId)).thenReturn(Optional.empty());
|         
|         // When & Then
|         assertThatThrownBy(() -> productService.getProductById(productId))
|             .isInstanceOf(ProductNotFoundException.class);
|     }
| }

🧪 Testing

**Test Framework & Tools:**
- **JUnit 5** - Modern unit testing framework with parameterized and nested test support
- **Mockito** - Powerful mocking framework for isolating units under test
- **AssertJ** - Fluent assertions for expressive test conditions

**Run tests:**
```bash
# Run all tests
mvn test

# Run specific test class
mvn -Dtest=ProductServiceTest test

# Run specific test method
mvn -Dtest=ProductServiceTest#getProductById_ExistingId_ReturnsProduct test

# Run with coverage report (requires jacoco plugin)
mvn test jacoco:report
```

**Testing Patterns Used:**
- `@ExtendWith(MockitoExtension.class)` for dependency injection of mocks
- `@Mock` for mocking dependencies
- `@InjectMocks` for injecting mocks into service under test
- `@BeforeEach` for test data setup
- `@Nested` and `@DisplayName` for organizing and describing test cases
- AssertJ fluent assertions: `assertThat().isEqualTo().isNotNull()`
- Mockito verification: `verify(repository).findById()`
- `assertThatThrownBy()` for exception testing

🚨 Error Handling

HTTP Status Codes
Status Code	Description	Usage
200 OK	Success	GET, PUT, DELETE operations
201 CREATED	Resource created	POST operations
400 BAD REQUEST	Invalid input	Validation errors
404 NOT FOUND	Resource not found	Missing product/user/cart
409 CONFLICT	Duplicate resource	Existing product/user
500 INTERNAL ERROR	Server error	Unexpected exceptions


🔒 Security

**Security Features Implemented:**
✅ **JWT Token-Based Authentication** - Stateless authentication using JSON Web Tokens (JJWT 0.12.6)
✅ **Role-Based Access Control (RBAC)** - Fine-grained endpoint protection using `@PreAuthorize("hasRole('ROLE_ADMIN')")` and `@PreAuthorize("hasRole('ROLE_USER')")`
✅ **Password Encryption** - BCryptPasswordEncoder for secure password hashing
✅ **Token Validation** - Comprehensive token validation with expiration checks
✅ **Security Filter Chain** - JWT authentication filter (`AuthTokenFilter`) integrated into Spring Security
✅ **Default Users** - DataInitializer creates 5 ROLE_USER and 2 ROLE_ADMIN users (password: "12345") for testing
✅ **Input Validation** - Jakarta Bean Validation for request validation
✅ **SQL Injection Prevention** - JPA parameterized queries
✅ **Exception Security** - Proper error handling without exposing system details

**JWT Token Details:**
- **Token Format**: `Bearer <JWT_TOKEN>`
- **Expiration**: 1 hour (configurable via `auth.token.expirationInMils`)
- **Secret Key**: Hex-encoded secret in `application.properties`
- **Claims**: Includes user ID, email, and roles

**Authentication Flow:**
1. User calls `/api/v1/auth/login` with email and password
2. Server validates credentials using Spring Security's AuthenticationManager
3. Server generates JWT token via `JwtUtils.generateTokenForUser()`
4. Client stores token and includes it in subsequent requests: `Authorization: Bearer <token>`
5. `AuthTokenFilter` intercepts requests and validates token
6. If valid, user identity and roles are loaded into SecurityContext

**Default Credentials (for development/testing):**
- **Admin Users**: admin1@email.com, admin2@email.com (password: "12345")
- **Regular Users**: user1@email.com to user5@email.com (password: "12345")

**Future Security Enhancements:**
- API rate limiting per user/IP
- Token refresh mechanism
- Social login integration (OAuth 2.0)
- Two-factor authentication (2FA)

🤝 Contributing
Contributions are what make the open-source community amazing!

1. How to Contribute
2. Fork the Project
git checkout -b feature/AmazingFeature

3. Commit your Changes
git commit -m 'Add some AmazingFeature'

4. Push to the Branch
git push origin feature/AmazingFeature

5. Open a Pull Request

Guidelines
. Follow Java coding conventions
. Write meaningful commit messages
. Update documentation as needed
. Add tests for new features



Create your Feature Branch
📧 Contact
Kumar Aman

GitHub: @Kumar-Aman7974

Email: amanbth7974@gmail.com

LinkedIn: [Add your LinkedIn URL]

Project Link: https://github.com/Kumar-Aman7974/Shopping-Cart-Backend-System

🙏 Acknowledgments
Spring Boot Documentation
Baeldung Tutorials
Daily Code Work tutorials
Open-source community


⭐ Show Your Support
If you found this project helpful, please give it a ⭐ on GitHub!
https://img.shields.io/github/stars/Kumar-Aman7974/Shopping-Cart-Backend-System?style=social

Built with ❤️ using Spring Boot | © 2026 Kumar Aman
## How to Add This README:

```bash
# 1. Create the README file with the content above
# You can either:
# - Copy the content and create README.md manually
# - Or use this command to create it:

# 2. If you have the content in clipboard, create the file:
notepad README.md
# Then paste the content and save

# 3. Move it to root (if needed) and commit
git add README.md
git commit -m "docs: add comprehensive README with complete documentation"
git push

# 4. Delete the old Readme.md if it exists in src folder
git rm src/main/java/com/dailycodework/demoshops/Readme.md
git commit -m "chore: remove old README from src folder"
git push