# CSRF Protection

## Learning Objectives

- Understand Cross-Site Request Forgery attacks
- Implement CSRF tokens in Spring applications
- Know when CSRF protection is and isn't needed
- Apply CSRF protection to forms and APIs

## Why This Matters

CSRF attacks trick authenticated users into performing unintended actions. This can lead to unauthorized transactions, data changes, or account modifications.

## The Concept

### How CSRF Works

```
1. User logs into bank.com (session cookie set)
2. User visits evil.com (while still logged into bank)
3. Evil.com contains: <img src="bank.com/transfer?to=attacker&amount=1000">
4. Browser sends request WITH session cookie
5. Bank processes transfer (user never intended this)
```

### Spring Security CSRF Protection

Spring Security enables CSRF protection by default for form-based applications.

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        );
    return http.build();
}
```

### Form Integration

```html
<!-- Thymeleaf: Token included automatically -->
<form th:action="@{/transfer}" method="post">
    <!-- Hidden token auto-inserted -->
    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
    <button type="submit">Transfer</button>
</form>
```

### API/JavaScript Integration

```javascript
// Read token from cookie or meta tag
const token = document.querySelector('meta[name="_csrf"]').content;
const header = document.querySelector('meta[name="_csrf_header"]').content;

fetch('/api/transfer', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        [header]: token  // X-CSRF-TOKEN: <token>
    },
    body: JSON.stringify(data)
});
```

### When to Disable CSRF

CSRF protection should be disabled for stateless APIs using token authentication:

```java
http.csrf(csrf -> csrf.disable());  // Only for stateless JWT APIs
```

| Scenario | CSRF Needed? |
|----------|--------------|
| Session-based web app | ✅ Yes |
| Form submissions | ✅ Yes |
| Stateless JWT API | ❌ No |
| Server-to-server API | ❌ No |

### Same-Site Cookies

Additional CSRF defense:

```java
@Bean
public CookieSerializer cookieSerializer() {
    DefaultCookieSerializer serializer = new DefaultCookieSerializer();
    serializer.setSameSite("Strict");  // or "Lax"
    return serializer;
}
```

| SameSite Value | Behavior |
|----------------|----------|
| Strict | Cookie only sent on same-site requests |
| Lax | Sent on same-site + top-level navigation |
| None | Sent on all requests (requires Secure) |

## Summary

- CSRF tricks users into unintended actions using their session
- Spring Security provides automatic CSRF token protection
- Include tokens in forms (auto with Thymeleaf) and AJAX requests
- Disable only for stateless APIs with token authentication
- Use SameSite cookies as additional defense

## Additional Resources

- [OWASP CSRF Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
- [Spring Security CSRF](https://docs.spring.io/spring-security/reference/features/exploits/csrf.html)
