# Security Logging and Monitoring

## Learning Objectives

- Understand security logging requirements
- Log security events effectively
- Avoid sensitive data in logs
- Enable security monitoring and alerting

## Why This Matters

Without proper logging, you can't detect attacks, investigate incidents, or prove compliance. Security logging is essential for detection and response.

## The Concept

### What to Log

| Event Type | Examples |
|------------|----------|
| Authentication | Login success/failure, logout |
| Authorization | Access denied, privilege changes |
| Data access | Sensitive data queries, exports |
| Modifications | User changes, role changes |
| Security events | Password resets, MFA changes |
| Anomalies | Rate limit hits, invalid inputs |

### What NOT to Log

| Never Log | Why |
|-----------|-----|
| Passwords | Can be compromised |
| Credit card numbers | PCI compliance |
| SSNs | Privacy laws |
| API keys/tokens | Security risk |
| Full session tokens | Session hijacking |

### Secure Logging Patterns

```java
// ❌ Sensitive data in logs
log.info("User {} logged in with password {}", username, password);
log.info("Processing credit card {}", cardNumber);

// ✅ Safe logging
log.info("Login successful for user: {}", username);
log.info("Processing credit card ending in: {}", maskCard(cardNumber));

private String maskCard(String card) {
    return "****-****-****-" + card.substring(card.length() - 4);
}
```

### Structured Security Logs

```java
@Slf4j
public class SecurityLogger {
    
    public void logAuthSuccess(String username, String ipAddress) {
        log.info("security_event=AUTH_SUCCESS username={} ip={} timestamp={}", 
            username, ipAddress, Instant.now());
    }
    
    public void logAuthFailure(String username, String ipAddress, String reason) {
        log.warn("security_event=AUTH_FAILURE username={} ip={} reason={} timestamp={}", 
            username, ipAddress, reason, Instant.now());
    }
    
    public void logAccessDenied(String username, String resource, String action) {
        log.warn("security_event=ACCESS_DENIED username={} resource={} action={} timestamp={}", 
            username, resource, action, Instant.now());
    }
}
```

### Spring Security Event Logging

```java
@Component
public class SecurityEventListener {
    
    private final SecurityLogger securityLogger;
    
    @EventListener
    public void onAuthSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        securityLogger.logAuthSuccess(username, getClientIP());
    }
    
    @EventListener
    public void onAuthFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        securityLogger.logAuthFailure(username, getClientIP(), "bad_credentials");
    }
}
```

### Log Aggregation

Send logs to centralized system for analysis:

```yaml
# logback-spring.xml for ELK Stack
<appender name="STASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
    <destination>logstash:5000</destination>
    <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
</appender>
```

### Alerting Rules

Configure alerts for suspicious patterns:

| Pattern | Alert |
|---------|-------|
| 5+ failed logins in 5 mins | Possible brute force |
| Admin login from new IP | Review required |
| Large data export | Potential data theft |
| Multiple access denied | Privilege escalation attempt |

### Audit Trail Requirements

For compliance, maintain immutable audit logs:

```java
@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id @GeneratedValue
    private Long id;
    
    private String action;
    private String username;
    private String resource;
    private String details;
    private Instant timestamp;
    private String ipAddress;
    
    // No setters after creation - immutable
}
```

## Summary

- Log all authentication, authorization, and security events
- Never log passwords, tokens, or sensitive personal data
- Use structured logging for easy analysis
- Send logs to centralized system for aggregation
- Configure alerts for suspicious patterns
- Maintain immutable audit trails for compliance

## Additional Resources

- [OWASP Logging Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Logging_Cheat_Sheet.html)
- [Spring Security Events](https://docs.spring.io/spring-security/reference/servlet/authentication/events.html)
