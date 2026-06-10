# AGENTS.md - Demo Shops E-Commerce Backend

Critical knowledge for AI agents working on this Spring Boot 3.x e-commerce application.

## 🏗️ Architecture Overview

**Layered Architecture Pattern:**
```
Controller (@RestController) → Service (Interface + Implementation) → Repository (Spring Data JPA) → MySQL Database
```

Key principles:
- **Services are always interfaces** (e.g., `IProductService` → `ProductService`). Create interface first, then implementation.
- **DTOs mandatory** between API and Service layers to prevent exposing entity relationships (use `ProductDto`, `OrderDto`)
- **ModelMapper bean** configured in `security/config/ShopConfig.java` handles entity ↔ DTO conversion using `convertToDto()` methods
- **API Prefix**: `/api/v1` (configured in `application.properties` as `api.prefix`)

## 🔐 Security & Authentication

**JWT-based stateless authentication:**
- `ShopConfig` defines security chain with `SessionCreationPolicy.STATELESS`
- `AuthTokenFilter` intercepts requests and validates JWT tokens
- JWT configuration: `auth.token.expirationInMils=3600000` (1 hour), secret in `auth.token.jwtSecret`
- **Role-based access**: Use `@PreAuthorize("hasRole('ROLE_ADMIN')")` on secured endpoints (see `ProductController.addProduct()`)
- **Default seed data**: DataInitializer creates 5 ROLE_USER and 2 ROLE_ADMIN users on startup with password "12345"
- **Password encoding**: BCryptPasswordEncoder exclusively

## 📦 Critical Patterns

**DTO Conversion Must Pattern:**
- Always convert entities before returning from API: `productService.convertToDto(product)`
- Services expose both entity and DTO methods: `getProductById(Long id)` returns entity, `convertToDto()` converts
- Example: ProductController line 60-62 shows this pattern

**Exception Handling:**
- `GlobalExceptionHandler` handles `AccessDeniedException` (403 Forbidden)
- Custom exceptions exist: `ProductNotFoundException`, `AlreadyExistsException`, `ResourceNotFoundException`
- **Missing pattern**: Standard REST exception handlers for 400/404/409 not fully implemented - add more `@ExceptionHandler` methods to `GlobalExceptionHandler`
- Controllers catch exceptions and return `ApiResponse("error", message)` (see ProductController lines 65-68)

**Request/Response Wrapper:**
- All API responses wrapped in `ApiResponse(message, data)` - do NOT deviate
- Request objects: `AddProductRequest`, `ProductUpdateRequest`, `LoginRequest` are used over raw entities

**Optional.ofNullable() Pattern:**
- Used throughout for safe category lookup in `ProductService` line 49-58
- Creates new categories if they don't exist during product creation

## 🗄️ Data Model Relationships

**Key entities in `model/` package:**
- `Product` → `Category` (ManyToOne, cascade PERSIST/MERGE only - **never DELETE**)
- `Product` ← `Image` (OneToMany, cascade ALL, orphanRemoval=true)
- `Cart` ← `CartItem` (OneToMany)
- `Order` ← `OrderItem` (OneToMany)
- `User` ↔ `Role` (ManyToMany)

**Critical constraints:**
- Product.category uses `@JsonIgnore` to prevent circular serialization
- Image cascade ALL with orphanRemoval - deleting product auto-deletes images
- Category cascade PERSIST/MERGE only to prevent accidental deletion of shared categories

## ⚙️ Build & Development Workflow

**Build commands:**
```bash
# Clean compile
mvn clean compile

# Package application
mvn package

# Run application
mvn spring-boot:run

# Run tests
mvn test

# Run specific test class
mvn -Dtest=YourTestClass test
```

**Key configurations** in `application.properties`:
- MySQL: `dream_shops_db` with root/Kumar@123! (DEV ONLY)
- File upload: max 10MB individual, 20MB total request
- JPA: `show-sql=true` for debugging (disable in production)
- Dialect: `org.hibernate.dialect.MySQLDialect`

## 🔍 Service Layer Conventions

**Service interface methods should reflect repository queries:**
- `getProductById(Long id)` → throws `ProductNotFoundException`
- `getProductsByCategory(String category)` → returns empty list if none found
- `addProduct(AddProductRequest request)` → validates "already exists" and throws `AlreadyExistsException`
- Instance methods check existence before operations: `if(productExists(request.getName(), request.getBrand()))`

**Service implementations always:**
1. Validate input (check existence, validate relationships)
2. Convert requests to entities
3. Handle category/relationship lookups (see ProductService lines 43-58)
4. Return entities, not DTOs (DTO conversion happens in Controller)

## 🌐 API Response Conventions

**All endpoints return `ApiResponse` wrapper:**
- Success: `new ApiResponse("success", convertedData)` with HTTP 200
- Creation: HTTP 201 status (not yet consistently applied)
- Not found: HTTP 404 with `new ApiResponse("error", message)`
- Already exists: HTTP 409 with AlreadyExistsException
- Unauthorized: HTTP 403 from GlobalExceptionHandler

**Common endpoints pattern** (see ProductController):
- `GET /api/v1/products/all` → list with DTO conversion
- `GET /api/v1/products/product/{id}/product` → single item (note: unusual URL pattern)
- `POST /api/v1/products/add` → secured with `@PreAuthorize("hasRole('ROLE_ADMIN')")`
- `PUT /api/v1/products/{id}` → Secure ADMIN-only operations
- `DELETE /api/v1/products/{id}` → Secure ADMIN-only operations

## 🧪 Testing Patterns

**Current state:** Minimal test coverage (`DemoShopsApplicationTests.java` only has context loading test)

**When adding tests:**
- Use `@SpringBootTest` for integration tests
- Use `@MockBean` for service dependencies
- Test both happy path and exception cases
- Follow naming: `*Tests.java` (e.g., `ProductServiceTests.java`)
- Use JUnit 5 (included in spring-boot-starter-test)

## 📝 Important Implementation Notes

**Lombok annotations used extensively:**
- `@Getter`, `@Setter`, `@NoArgsConstructor` on entities
- `@RequiredArgsConstructor` on services/controllers for constructor injection
- `@Data` on DTOs and response objects
- `@AllArgsConstructor` not used - avoids exposing constructors with null defaults

**Transactional behavior:**
- `DataInitializer` uses `@Transactional` for seed data operations
- Services don't explicitly mark `@Transactional` (inherited from Spring transaction management)

**Common pitfalls to avoid:**
- ❌ Returning entities directly from API (always convert with DTO)
- ❌ Breaking Category cascade rules (PERSIST/MERGE only)
- ❌ Missing `@PreAuthorize` on admin-only operations
- ❌ Not wrapping responses in `ApiResponse`
- ❌ Throwing generic Exception instead of custom typed exceptions

## 🔗 Key Files Reference

| File | Purpose |
|------|---------|
| `security/config/ShopConfig.java` | Security beans, auth manager, ModelMapper |
| `exceptions/GlobalExceptionHandler.java` | Centralized exception handling (incomplete) |
| `data/DataInitializer.java` | Seed roles and users on startup |
| `response/ApiResponse.java` | Standard response wrapper |
| `service/product/IProductService.java` | Service contract example |
| `service/product/ProductService.java` | Service implementation with patterns |
| `controller/ProductController.java` | API endpoint patterns with @PreAuthorize |

---

**Framework Versions:** Spring Boot 3.3.5, Java 21, JUnit 5, Lombok 1.18.x, ModelMapper 3.2.0, JJWT 0.12.6

