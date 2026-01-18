# Trainee Exercise: Security Vulnerability Hunt

## Overview

**Duration:** 2-3 hours  
**Type:** Security Review  
**Mode:** Find and Fix

## Learning Objectives

- Identify security vulnerabilities in code
- Apply secure coding fixes
- Explain why each fix matters
- Practice security code review

## Prerequisites

- Completed secure coding readings
- Understanding of OWASP Top 10
- Spring Boot knowledge

## The Challenge

You've inherited a codebase with multiple security vulnerabilities. Your job is to find and fix them all.

---

## Vulnerable Application Code

Review the following code snippets and identify ALL security issues.

### File 1: UserController.java

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    private static final String API_KEY = "sk_live_abc123xyz789";
    
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name) {
        String query = "SELECT * FROM users WHERE name LIKE '%" + name + "%'";
        return jdbcTemplate.query(query, new UserRowMapper());
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        log.info("Login attempt for user: {} with password: {}", 
            request.getUsername(), request.getPassword());
        
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            return "Login successful";
        }
        return "Login failed";
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setPassword(user.getPassword());  // Store password
        return userRepository.save(user);
    }
}
```

### File 2: OrderController.java

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}
```

### File 3: comment.html (Thymeleaf template)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Comments</title>
</head>
<body>
    <h1>User Comments</h1>
    
    <div th:each="comment : ${comments}">
        <p th:utext="${comment.text}"></p>
        <small th:text="${comment.author}"></small>
    </div>
    
    <form action="/api/comments" method="post">
        <textarea name="text"></textarea>
        <button type="submit">Post Comment</button>
    </form>
</body>
</html>
```

### File 4: application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/myapp
spring.datasource.username=root
spring.datasource.password=admin123

# Debug mode
logging.level.root=DEBUG
spring.h2.console.enabled=true

server.error.include-stacktrace=always
```

---

## Instructions

### Part 1: Identify Vulnerabilities (45 minutes)

For each file, list ALL security vulnerabilities you find.

**Format:**

| File | Line | Vulnerability Type | Description |
|------|------|-------------------|-------------|
| UserController.java | 12 | SQL Injection | String concatenation in query |
| ... | ... | ... | ... |

**Hint:** There are at least 15 vulnerabilities across all files.

---

### Part 2: Fix the Vulnerabilities (1 hour)

Provide the corrected code for each file.

**Requirements:**

- Fix ALL identified vulnerabilities
- Add comments explaining each fix
- Ensure code still functions correctly

---

### Part 3: Security Explanation (30 minutes)

For FIVE of the most critical vulnerabilities:

1. **What it is:** Name the vulnerability type
2. **Risk:** What could an attacker do?
3. **Fix:** How you fixed it
4. **Prevention:** How to prevent in future code

---

### Part 4: Security Configuration (30 minutes)

Create a `SecurityConfig.java` file that:

1. Requires authentication for all `/api/**` endpoints
2. Allows public access to login endpoint
3. Uses BCrypt for password encoding
4. Configures CSRF appropriately
5. Sets secure session management

---

## Deliverable

Submit a document containing:

1. **Vulnerability Table** - All identified issues
2. **Fixed Code Files** - All four files corrected
3. **Top 5 Explanations** - Detailed analysis
4. **SecurityConfig.java** - Spring Security configuration

---

## Grading Criteria

| Criteria | Points |
|----------|--------|
| Vulnerabilities Identified (15+ required) | 30 |
| Code Fixes (all files) | 30 |
| Top 5 Explanations | 20 |
| Security Configuration | 15 |
| Documentation Quality | 5 |
| **Total** | **100** |

---

## Hints

Categories of vulnerabilities to look for:

- Injection (SQL, etc.)
- Broken Authentication
- Sensitive Data Exposure
- Broken Access Control
- XSS
- Security Misconfiguration
- CSRF

---

## Answer Key (For Instructor Only)

<details>
<summary>Click to reveal vulnerability count per file</summary>

- UserController.java: 7 vulnerabilities
- OrderController.java: 3 vulnerabilities
- comment.html: 2 vulnerabilities
- application.properties: 4 vulnerabilities

</details>
