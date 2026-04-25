# 🛒 Shopping Cart Backend System

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
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
- 🧪 **Testable Code** with separation of concerns

## 🛠️ Technology Stack

### Backend Technologies


### Detailed Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 LTS | Core programming language |
| **Spring Boot** | 2.7.x | Application framework |
| **Spring Data JPA** | - | ORM and database operations |
| **Spring Web MVC** | - | REST API development |
| **Hibernate** | 5.6.x | JPA implementation |
| **Maven** | 3.8+ | Dependency management |
| **MySQL** | 8.0+ | Production database |
| **H2 Database** | - | Development/Testing |
| **Lombok** | 1.18.x | Boilerplate code reduction |
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
# Should output: Java 17 or higher

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
POST	/auth/register	Register new user	User details	User object
POST	/auth/login	User login	Credentials	Auth token
GET	/users/{id}	Get user profile	-	User details
PUT	/users/{id}	Update user	Updated details	Updated user

Product APIs
Method	Endpoint	Description	Request Body	Response
GET	/products	Get all products	-	List of products
GET	/products/{id}	Get product by ID	-	Product object
GET	/products/category/{categoryId}	Get products by category	-	List of products
POST	/products	Create new product	Product details	Created product
PUT	/products/{id}	Update product	Updated details	Updated product
DELETE	/products/{id}	Delete product	-	Success message

Example Request - Create Product:
POST /api/products
{
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

Method	Endpoint	Description	Request Body	Response
GET	/cart/{userId}	Get user's cart	-	Cart object
POST	/cart/add	Add item to cart	Cart item details	Updated cart
PUT	/cart/update	Update cart item quantity	Cart item details	Updated cart
DELETE	/cart/remove/{cartItemId}	Remove from cart	-	Success message
DELETE	/cart/clear/{userId}	Clear entire cart	-	Success message

Example Request - Add to Cart:
POST /api/cart/add
{
    "userId": 1,
    "productId": 1,
    "quantity": 2
}

Order APIs
Method	Endpoint	Description	Request Body	Response
POST	/orders	Place order	Order details	Order object
GET	/orders/{userId}	Get user orders	-	List of orders
GET	/orders/{orderId}	Get order by ID	-	Order details
PUT	/orders/{orderId}/status	Update order status	Status update	Updated order
DELETE	/orders/{orderId}	Cancel order	-	Success message

Category APIs
Method	Endpoint	Description	Request Body	Response
GET	/categories	Get all categories	-	List of categories
GET	/categories/{id}	Get category by ID	-	Category object
POST	/categories	Create category	Category name	Created category
PUT	/categories/{id}	Update category	Updated name	Updated category
DELETE	/categories/{id}	Delete category	-	Success message

🗄️ Database Schema
Entity Relationship Diagram
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│    User     │     │    Cart     │     │   Order     │
├─────────────┤     ├─────────────┤     ├─────────────┤
│ id (PK)     │────▶│ id (PK)     │     │ id (PK)     │
│ firstName   │     │ user_id(FK) │     │ user_id(FK) │◀────┐
│ lastName    │     │ totalAmount │     │ orderDate   │     │
│ email       │     └─────────────┘     │ totalAmount │     │
│ password    │           │             │ status      │     │
│ createdAt   │           │             └─────────────┘     │
└─────────────┘           │                    │           │
                          ▼                    ▼           │
                   ┌─────────────┐     ┌─────────────┐     │
                   │  CartItem   │     │  OrderItem  │     │
                   ├─────────────┤     ├─────────────┤     │
                   │ id (PK)     │     │ id (PK)     │     │
                   │ cart_id(FK) │     │ order_id(FK)│─────┘
                   │ product_id  │     │ product_id  │
                   │ quantity    │     │ quantity    │
                   │ price       │     │ price       │
                   └─────────────┘     └─────────────┘
                          │                    │
                          ▼                    ▼
                   ┌─────────────────────────────┐
                   │          Product            │
                   ├─────────────────────────────┤
                   │ id (PK)                     │
                   │ name                        │
                   │ brand                       │
                   │ price                       │
                   │ inventory                   │
                   │ description                 │
                   │ category_id (FK)            │
                   └─────────────────────────────┘
                                   │
                                   ▼
                   ┌─────────────────────────────┐
                   │          Category           │
                   ├─────────────────────────────┤
                   │ id (PK)                     │
                   │ name                        │
                   └─────────────────────────────┘


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
│   │   │   ├── exceptions/                    # Custom Exceptions
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── ProductNotFoundException.java
│   │   │   │   └── AlreadyExistsException.java
│   │   │   │
│   │   │   └── enums/                         # Enumerations
│   │   │       └── OrderStatus.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties        # Configuration
│   │       └── static/                       # Static resources
│   │
│   └── test/                                  # Unit Tests
│       └── java/com/dailycodework/demoshops/
│           └── DemoShopsApplicationTests.java
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

Example 3: Global Exception Handler
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse("error", ex.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse("error", "An unexpected error occurred"));
    }
}

🧪 Testing
# Run all tests
mvn test

# Run specific test class
mvn -Dtest=ProductServiceTest test

# Run with coverage report
mvn test jacoco:report

### TestMu JUnit Assignment (PowerShell)

Run these from project root (`demo-shops`) in PowerShell.

```powershell
# Option 1: Full Grid URL (recommended if provided by TestMu)
$env:TESTMU_GRID_URL = "https://<username>:<access_key>@hub.testmuai.com/wd/hub"

# Option 2: Username + Access Key (use this if TESTMU_GRID_URL is not set)
$env:TESTMU_USERNAME = "<your_testmu_username>"
$env:TESTMU_ACCESS_KEY = "<your_testmu_access_key>"
```

```powershell
# Run only the assignment suite
.\mvnw -Dtest=TestMuJUnitAssignmentTest test
```

```powershell
# Optional: clear env vars after run
Remove-Item Env:TESTMU_GRID_URL -ErrorAction SilentlyContinue
Remove-Item Env:TESTMU_USERNAME -ErrorAction SilentlyContinue
Remove-Item Env:TESTMU_ACCESS_KEY -ErrorAction SilentlyContinue
```

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
Security Features Implemented
✅ Password encryption (BCrypt)
✅ Input validation
✅ SQL injection prevention (JPA)
✅ CORS configuration
✅ Rate limiting ready

Future Security Enhancements
JWT authentication
Role-based access control (RBAC)
API rate limiting
Request validation filters

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