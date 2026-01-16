# Data Protection and Encryption

## Learning Objectives

- Understand data protection requirements
- Apply encryption for data at rest and in transit
- Handle sensitive data appropriately
- Implement secure data storage patterns

## Why This Matters

Data breaches expose sensitive information and damage trust. Proper data protection is both a security requirement and often a legal obligation.

## The Concept

### Data Protection Layers

```
┌─────────────────────────────────────────────────────────────┐
│                 DATA PROTECTION LAYERS                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  IN TRANSIT         │  AT REST            │  IN USE        │
│  ──────────────     │  ─────────          │  ──────        │
│  • HTTPS/TLS        │  • Database encrypt │  • Memory      │
│  • Secure websocket │  • File encryption  │  • Logging     │
│  • mTLS for services│  • Key management   │  • Display     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Encryption in Transit (HTTPS)

```yaml
# application.yml - Enforce HTTPS
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: ${SSL_KEYSTORE_PASSWORD}
    key-store-type: PKCS12
```

```java
// Force HTTPS in Spring Security
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.requiresChannel(channel -> 
        channel.anyRequest().requiresSecure()
    );
    return http.build();
}
```

### Encryption at Rest

#### Database-Level Encryption

Most databases support Transparent Data Encryption (TDE). Enable in database configuration.

#### Application-Level Encryption

```java
@Service
public class EncryptionService {
    
    private final SecretKey secretKey;
    
    public String encrypt(String plaintext) {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(UTF_8));
        // Combine IV and ciphertext
        return Base64.getEncoder().encodeToString(combine(iv, encrypted));
    }
    
    public String decrypt(String ciphertext) {
        byte[] combined = Base64.getDecoder().decode(ciphertext);
        byte[] iv = extractIv(combined);
        byte[] encrypted = extractData(combined);
        
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
        return new String(cipher.doFinal(encrypted), UTF_8);
    }
}
```

### Sensitive Data Handling

#### Data Classification

| Classification | Examples | Handling |
|----------------|----------|----------|
| Public | Marketing content | No restrictions |
| Internal | Employee names | Access control |
| Confidential | Customer PII | Encryption, access control |
| Restricted | Passwords, keys | Never store plaintext |

#### PII Protection

```java
// ❌ Logging PII
log.info("Processing user: {}, SSN: {}", user, ssn);

// ✅ Mask sensitive data
log.info("Processing user: {}, SSN: ***-**-{}", user, ssn.substring(7));

// ❌ Returning sensitive data
return new UserDTO(user.getName(), user.getEmail(), user.getSsn());

// ✅ Limit exposed data
return new UserDTO(user.getName(), maskEmail(user.getEmail()), null);
```

### Key Management

```java
// ❌ Never hardcode secrets
private static final String SECRET = "my-secret-key";

// ✅ Use environment variables
@Value("${encryption.key}")
private String encryptionKey;

// ✅ Better: Use a secrets manager
@Autowired
private SecretsManager secretsManager;

public String getKey() {
    return secretsManager.getSecret("encryption-key");
}
```

### Secure Data Storage Patterns

#### Password Hashing (Not Encryption)

```java
// Passwords are HASHED, not encrypted
// You should never be able to retrieve original password

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
}

// Store
user.setPasswordHash(passwordEncoder.encode(rawPassword));

// Verify
boolean valid = passwordEncoder.matches(rawPassword, user.getPasswordHash());
```

#### API Keys and Tokens

```java
// Hash API keys for storage
String apiKeyHash = MessageDigest.getInstance("SHA-256")
    .digest(apiKey.getBytes());

// Compare provided key against stored hash
```

### Secure Data Deletion

```java
// When deleting sensitive data
public void deleteUserData(Long userId) {
    // Delete from all locations
    userRepository.deleteById(userId);
    auditLogRepository.deleteByUserId(userId);
    cacheManager.evict("users", userId);
    
    // For compliance: document deletion
    deletionLogRepository.save(new DeletionLog(userId, LocalDateTime.now()));
}
```

## Summary

- Encrypt in transit (HTTPS/TLS) and at rest (database/application)
- Classify data and handle according to sensitivity
- Never store passwords in plaintext—use bcrypt hashing
- Never hardcode secrets—use environment variables or secrets managers
- Be cautious with logging and exposing sensitive data

## Additional Resources

- [OWASP Cryptographic Storage Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cryptographic_Storage_Cheat_Sheet.html)
- [NIST Encryption Guidelines](https://csrc.nist.gov/publications/detail/sp/800-175b/rev-1/final)
