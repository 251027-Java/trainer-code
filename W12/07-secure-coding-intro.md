# Secure Coding Introduction

## Learning Objectives

- Understand the importance of secure coding
- Recognize common security vulnerabilities
- Apply security-first thinking to development
- Know the OWASP Top 10 categories

## Why This Matters

Security breaches are costly and damaging. As developers, we are the first line of defense. Building security into our code from the start is far more effective than trying to add it later.

## The Concept

### The Cost of Security Failures

| Impact | Examples |
|--------|----------|
| Financial | Fines, lawsuits, breach costs |
| Reputation | Lost customer trust |
| Operational | Downtime, incident response |
| Legal | Regulatory penalties, compliance |

### Security-First Mindset

```
┌─────────────────────────────────────────────────────────────┐
│             SECURITY IN DEVELOPMENT                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  OLD WAY:  Build → Test → Deploy → Add Security            │
│                                                             │
│  NEW WAY:  Security integrated at every step               │
│                                                             │
│  Design   → Secure design patterns                         │
│  Code     → Secure coding practices                        │
│  Test     → Security testing                               │
│  Deploy   → Secure configuration                           │
│  Monitor  → Security monitoring                            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### OWASP Top 10 (2021)

The Open Web Application Security Project identifies critical risks:

| # | Category | Description |
|---|----------|-------------|
| 1 | **Broken Access Control** | Users acting beyond permissions |
| 2 | **Cryptographic Failures** | Poor encryption/data exposure |
| 3 | **Injection** | SQL, NoSQL, OS command injection |
| 4 | **Insecure Design** | Missing security controls |
| 5 | **Security Misconfiguration** | Default/incomplete config |
| 6 | **Vulnerable Components** | Outdated dependencies |
| 7 | **Auth Failures** | Weak authentication |
| 8 | **Software/Data Integrity** | Untrusted updates/data |
| 9 | **Logging/Monitoring Failures** | Can't detect attacks |
| 10 | **SSRF** | Server-side request forgery |

### Core Security Principles

#### Defense in Depth

Multiple layers of security:

```
Firewall → WAF → Authentication → Authorization → Input Validation → Encryption
```

#### Least Privilege

Grant minimum necessary permissions:

```
❌ User can access all tables
✅ User can access only their data
```

#### Fail Securely

When errors occur, fail closed:

```java
try {
    authorize(user);
} catch (Exception e) {
    // ❌ allow() - fails open
    // ✅ deny() - fails closed
    deny();
}
```

#### Don't Trust Input

All external data is potentially malicious:

- User forms
- API requests
- File uploads
- URL parameters
- HTTP headers

### Common Vulnerability Patterns

| Pattern | Example | Prevention |
|---------|---------|------------|
| Injection | SQL in form field | Parameterized queries |
| XSS | JavaScript in comments | Output encoding |
| Broken auth | Weak passwords | Strong auth requirements |
| Data exposure | Logs with passwords | Data classification |
| Missing controls | No rate limiting | Security requirements |

### Security in Java/Spring

```java
// ❌ Insecure
String query = "SELECT * FROM users WHERE id = " + userId;

// ✅ Secure
@Query("SELECT u FROM User u WHERE u.id = :id")
User findById(@Param("id") Long id);
```

```java
// ❌ Insecure
log.info("User login: " + username + " password: " + password);

// ✅ Secure
log.info("User login attempt for: {}", username);
```

### Developer Responsibilities

1. **Know the risks** - Understand OWASP Top 10
2. **Validate input** - Never trust external data
3. **Use frameworks** - Spring Security, parameterized queries
4. **Keep updated** - Patch dependencies
5. **Test for security** - Include security tests
6. **Review code** - Look for security issues

## Summary

- Security must be built in from the start
- OWASP Top 10 identifies critical web security risks
- Core principles: defense in depth, least privilege, fail securely
- Never trust input—validate and sanitize everything
- Use security frameworks and keep dependencies updated

## Additional Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [OWASP Secure Coding Practices](https://owasp.org/www-project-secure-coding-practices/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
