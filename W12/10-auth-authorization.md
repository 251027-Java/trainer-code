# Authentication and Authorization

## Learning Objectives

- Distinguish between authentication and authorization
- Implement secure authentication patterns
- Apply authorization controls at multiple levels
- Use Spring Security for access control

## Why This Matters

Authentication and authorization failures are among the most exploited vulnerabilities. Getting these right protects your users and your data.

## The Concept

### Authentication vs Authorization

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  AUTHENTICATION (AuthN)    │  AUTHORIZATION (AuthZ)        │
│  ─────────────────────     │  ─────────────────────        │
│  "Who are you?"            │  "What can you do?"           │
│                            │                                │
│  • Login credentials       │  • Role-based access          │
│  • Identity verification   │  • Permission checks          │
│  • Session management      │  • Resource access control    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Authentication Best Practices

#### Strong Password Requirements

```java
public void validatePassword(String password) {
    if (password.length() < 12) {
        throw new WeakPasswordException("Minimum 12 characters");
    }
    if (!password.matches(".*[A-Z].*")) {
        throw new WeakPasswordException("Requires uppercase");
    }
    if (!password.matches(".*[a-z].*")) {
        throw new WeakPasswordException("Requires lowercase");
    }
    if (!password.matches(".*\\d.*")) {
        throw new WeakPasswordException("Requires number");
    }
    if (!password.matches(".*[!@#$%^&*].*")) {
        throw new WeakPasswordException("Requires special character");
    }
}
```

#### Secure Password Storage

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);  // Work factor of 12
}

// When storing
String hashed = passwordEncoder.encode(rawPassword);

// When verifying
boolean matches = passwordEncoder.matches(rawPassword, storedHash);
```

#### Session Management

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .maximumSessions(1)  // One session per user
        .expiredSessionStrategy(...)  // Handle expired
    );
}
```

### Authorization Patterns

#### Role-Based Access Control (RBAC)

```java
// Controller level
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/users")
public List<User> getAllUsers() { ... }

// Method level
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public Report generateReport() { ... }
```

#### Resource-Based Authorization

```java
@GetMapping("/orders/{id}")
public Order getOrder(@PathVariable Long id, Principal principal) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    
    // Verify ownership
    if (!order.getUsername().equals(principal.getName())) {
        throw new AccessDeniedException("Not authorized");
    }
    
    return order;
}
```

#### Spring Security Configuration

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/public/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .requestMatchers("/api/users/**").authenticated()
            .anyRequest().denyAll()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .failureHandler(customFailureHandler)
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
        );
    
    return http.build();
}
```

### Common Vulnerabilities

| Vulnerability | Prevention |
|---------------|------------|
| Weak passwords | Enforce complexity, length |
| Credential stuffing | Rate limiting, MFA |
| Session fixation | Regenerate session on login |
| Privilege escalation | Verify authorization on every request |
| Broken access control | Check ownership, not just authentication |

### Defense Checklist

```java
// ✅ Verify authentication
if (principal == null) {
    throw new AuthenticationException("Not authenticated");
}

// ✅ Verify authorization (role)
if (!hasRole(principal, "ADMIN")) {
    throw new AccessDeniedException("Not authorized");
}

// ✅ Verify resource ownership
if (!resource.getOwner().equals(principal.getName())) {
    throw new AccessDeniedException("Not your resource");
}
```

### JWT Considerations

```java
// JWT should be:
// - Short-lived (15 mins - 1 hour)
// - Signed with strong algorithm (RS256)
// - NOT contain sensitive data

// Validate on every request
if (jwt.isExpired()) {
    throw new TokenExpiredException();
}
if (!jwt.isSignatureValid()) {
    throw new InvalidTokenException();
}
```

## Summary

- Authentication verifies identity; authorization verifies permissions
- Use BCrypt with high work factor for passwords
- Apply authorization at controller and service levels
- Always verify resource ownership, not just roles
- Configure Spring Security properly for your endpoints

## Additional Resources

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [OWASP Authentication Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html)
