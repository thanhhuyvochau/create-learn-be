# Agent Guidelines for Create-Learn-BE

This document provides essential information for agentic coding agents operating in this Spring Boot backend repository.

## Build & Test Commands

### Build
```bash
# Full build with tests
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Build for production
mvn clean install -Pprod
```

### Test
```bash
# Run all tests
mvn test

# Run single test class
mvn test -Dtest=CreateLearnBeApplicationTests

# Run single test method
mvn test -Dtest=CreateLearnBeApplicationTests#contextLoads

# Run with coverage
mvn test jacoco:report
```

### Run Application
```bash
# Development (local profile)
mvn spring-boot:run -Pdev

# Production
mvn spring-boot:run -Pprod

# With specific properties
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## Code Style Guidelines

### Package & Import Organization
- Package structure: `org.project.createlearnbe.[feature-area]`
- Main packages: `controllers`, `serivce` (note: typo - "serivce" instead of "service"), `repositories`, `mapper`, `entities`, `dto`, `config`, `utils`, `constant`
- Organize imports: static imports first, then java.*, javax.*, org.springframework.*, then custom packages
- Remove unused imports during refactoring

### Naming Conventions
- **Classes**: PascalCase (e.g., `AccountService`, `TeacherController`, `NewsMapper`)
- **Methods/Variables**: camelCase (e.g., `getAllNews()`, `createAccount()`, `newsRepository`)
- **Constants**: UPPER_SNAKE_CASE
- **Package names**: lowercase (e.g., `org.project.createlearnbe.controllers`)
- **DTOs**: Use suffix for clarity (e.g., `NewsRequest`, `NewsResponse`, `AccountResponse`)
- **Database entities**: Singular nouns (e.g., `News`, `Teacher`, `Account`)

### Class Structure
Use Lombok annotations to reduce boilerplate:
- `@RequiredArgsConstructor` for dependency injection (preferred over `@Autowired`)
- `@Getter` / `@Setter` for entity properties
- `@Data` for DTOs (includes @Getter, @Setter, @ToString, @EqualsAndHashCode)

Example:
```java
@Service
@RequiredArgsConstructor
public class NewsService {
  private final NewsRepository newsRepository;
  private final NewsMapper newsMapper;
  private final UrlUtils urlUtils;
}
```

### Entity Mapping
- Use MapStruct for DTO conversions: `@Mapper(componentModel = "spring")`
- Create separate mappers per entity (e.g., `TeacherMapper`, `NewsMapper`)
- Use `@AfterMapping` hooks for post-processing (building URLs, etc.)
- Methods: `toDto()`, `toEntity()`, `toResponse()`, `updateEntityFromRequest()`

### Controller Patterns
- Use `@RestController` and `@RequestMapping` annotations
- Wrap responses with `ApiWrapper<T>` for consistency
- Use `@Tag` and `@Operation` (OpenAPI/Swagger) annotations for documentation
- Add `@CrossOrigin("*")` for CORS support
- Validate input with `@Valid` on request bodies
- Return `ResponseEntity<ApiWrapper<T>>` for consistent response format

### Error Handling
- Create specific exception types (e.g., `UserNameDuplicateException`, `EmailDuplicateException`)
- Extend `RuntimeException` or create custom base exception
- Use `@RestControllerAdvice` with `@ExceptionHandler` methods
- Custom handler example:
  ```java
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
    // Build error response
    return ResponseEntity.badRequest().body(ApiWrapper.error(message, status));
  }
  ```
- Always return errors wrapped in `ApiWrapper.error(message, statusCode)`

### Repository Pattern
- Extend `JpaRepository<Entity, ID>`
- Use Spring Data query methods for common operations
- Custom queries: use `@Query` annotation with JPQL or native SQL
- Follow naming: `find[By/All][Condition]`, `delete[By]`, etc.

### Service Layer
- Business logic and transaction management
- Use constructor injection with `@RequiredArgsConstructor`
- Call repositories and mappers for data operations
- Throw meaningful exceptions; avoid generic `RuntimeException` where custom types exist
- Handle Optional with `.orElseThrow()` for cleaner code

### Formatting & Structure
- Line length: 100-120 characters
- Indentation: 2 spaces (as shown in codebase)
- Method chaining: Preferred for fluent APIs (e.g., `repository.findAll().map().orElseThrow()`)
- Lambda expressions: Use for stream operations and functional interfaces
- Braces: Always use braces, even for single-line blocks

### Technology Stack Details
- **Java Version**: 21
- **Framework**: Spring Boot 3.5.5
- **Database**: MySQL (via spring-boot-starter-data-jpa)
- **Mapper**: MapStruct 1.6.3
- **Annotations**: Lombok 1.18.34 (handles constructor generation)
- **API Docs**: SpringDoc OpenAPI (Swagger UI integration)
- **Security**: Spring Security
- **Testing**: JUnit 5 (Jupiter), Spring Boot Test
- **Storage**: MinIO 8.5.7
- **Authentication**: JWT (jjwt 0.11.5)

### Configuration & Profiles
- Profiles: `local` (dev) and `prod`
- Properties files: `application.yml`, `application-local.yml`, `application-prod.yml`
- Run with specific profile: `spring.profiles.active=local`

### Auditing
- Use `Auditable` mixin for automatic `createdAt`, `updatedAt`, `createdBy`, `updatedBy` fields
- Implement `AuditorAware` via `AuditorAwareImpl` for capturing current user

## Key Patterns & Best Practices

1. **Dependency Injection**: Constructor injection via `@RequiredArgsConstructor` (not field injection)
2. **Response Wrapping**: All API responses wrapped in `ApiWrapper` for consistency
3. **Pagination**: Use Spring Data's `Pageable` and return `Page<T>`
4. **Error Response**: Standardized error messages and HTTP status codes
5. **URL Building**: Use `UrlUtils.buildAbsolutePath()` for dynamic content URLs
6. **Null Safety**: Use Optional and method references where applicable
7. **API Documentation**: Leverage OpenAPI annotations for auto-generated documentation

## Common Mistakes to Avoid

- Don't use field injection (`@Autowired`); use constructor injection
- Don't create generic exception handling; use specific exception types
- Don't forget `@Valid` annotation on DTOs in controller methods
- Don't skip mapping setup in mappers; ensure all fields are covered
- Don't use raw SQL without proper parameterization (use `@Query` with named parameters)
