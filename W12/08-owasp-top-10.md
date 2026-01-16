# OWASP Top 10

## Learning Objectives

- Understand each OWASP Top 10 vulnerability category
- Recognize these vulnerabilities in code
- Apply prevention techniques for each category
- Prioritize security efforts based on risk

## Why This Matters

The OWASP Top 10 represents the most critical security risks to web applications. Understanding these vulnerabilities is essential for writing secure code.

## The Concept

### 1. Broken Access Control (A01:2021)

**What:** Users accessing data or functions beyond their permissions.

**Examples:**

- Viewing other users' data by changing URL ID
- Accessing admin endpoints without admin role
- Modifying data you should only read

**Prevention:**

```java
// ❌ Vulnerable
@GetMapping("/orders/{id}")
public Order getOrder(@PathVariable Long id) {
    return orderRepository.findById(id).orElseThrow();
}

// ✅ Secure
@GetMapping("/orders/{id}")
public Order getOrder(@PathVariable Long id, Principal principal) {
    Order order = orderRepository.findById(id).orElseThrow();
    if (!order.getUsername().equals(principal.getName())) {
        throw new AccessDeniedException("Not your order");
    }
    return order;
}
```

---

### 2. Cryptographic Failures (A02:2021)

**What:** Sensitive data exposed due to weak or missing encryption.

**Examples:**

- Storing passwords in plaintext
- Using HTTP instead of HTTPS
- Weak encryption algorithms

**Prevention:**

```java
// ❌ Vulnerable
user.setPassword(password);

// ✅ Secure
@Autowired
private PasswordEncoder passwordEncoder;

user.setPassword(passwordEncoder.encode(password));
```

---

### 3. Injection (A03:2021)

**What:** Untrusted data sent to an interpreter as part of a command.

**Examples:**

- SQL injection
- OS command injection
- LDAP injection

**Prevention:**

```java
// ❌ Vulnerable (SQL Injection)
String query = "SELECT * FROM users WHERE name = '" + name + "'";

// ✅ Secure (Parameterized Query)
@Query("SELECT u FROM User u WHERE u.name = :name")
User findByName(@Param("name") String name);
```

---

### 4. Insecure Design (A04:2021)

**What:** Security flaws in the design, not just implementation.

**Examples:**

- No rate limiting on login
- Security questions as password reset
- Missing fraud detection

**Prevention:**

- Threat modeling during design
- Security requirements in user stories
- Abuse case testing

---

### 5. Security Misconfiguration (A05:2021)

**What:** Insecure default or incomplete configurations.

**Examples:**

- Default admin credentials
- Enabled debug endpoints in production
- Missing security headers

**Prevention:**

```yaml
# application-prod.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info  # Not all endpoints

spring:
  h2:
    console:
      enabled: false  # Disable H2 console in prod
```

---

### 6. Vulnerable and Outdated Components (A06:2021)

**What:** Using libraries with known vulnerabilities.

**Prevention:**

```xml
<!-- Use Dependabot or Snyk for alerts -->
<!-- Update regularly -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <version>3.2.0</version> <!-- Keep current -->
</dependency>
```

---

### 7. Identification and Authentication Failures (A07:2021)

**What:** Weak authentication mechanisms.

**Examples:**

- Weak password requirements
- No multi-factor authentication
- Session fixation

**Prevention:**

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);  // Strong hashing
}

// Enforce password complexity
if (password.length() < 12) {
    throw new WeakPasswordException("Minimum 12 characters");
}
```

---

### 8. Software and Data Integrity Failures (A08:2021)

**What:** Code/data integrity not verified.

**Examples:**

- Insecure deserialization
- Unsigned updates
- CI/CD pipeline compromise

**Prevention:**

```java
// Avoid deserializing untrusted data
// Use JSON with explicit type mapping instead of Java serialization
ObjectMapper mapper = new ObjectMapper();
mapper.activateDefaultTyping(/* careful configuration */);
```

---

### 9. Security Logging and Monitoring Failures (A09:2021)

**What:** Insufficient logging to detect attacks.

**Prevention:**

```java
log.info("Login failed for user: {}", username);
log.warn("Access denied to {} for resource {}", user, resource);
log.error("Potential SQL injection attempt: {}", sanitize(input));

// Ship logs to SIEM for correlation
```

---

### 10. Server-Side Request Forgery (A10:2021)

**What:** Attacker tricks server into making requests.

**Prevention:**

```java
// ❌ Vulnerable - takes any URL
public String fetchUrl(String url) {
    return restTemplate.getForObject(url, String.class);
}

// ✅ Secure - validate URL
public String fetchUrl(String url) {
    if (!isAllowedHost(url)) {
        throw new SecurityException("URL not allowed");
    }
    return restTemplate.getForObject(url, String.class);
}
```

## Summary

| Category | Key Prevention |
|----------|---------------|
| Access Control | Verify permissions on every request |
| Crypto | Use strong encryption, HTTPS |
| Injection | Parameterized queries |
| Design | Threat modeling |
| Configuration | Harden defaults |
| Components | Keep updated |
| Auth | Strong passwords, MFA |
| Integrity | Verify sources |
| Logging | Log security events |
| SSRF | Validate URLs |

## Additional Resources

- [OWASP Top 10 Official](https://owasp.org/www-project-top-ten/)
- [OWASP Cheat Sheets](https://cheatsheetseries.owasp.org/)
