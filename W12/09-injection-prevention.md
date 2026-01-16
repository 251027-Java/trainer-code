# Injection Prevention

## Learning Objectives

- Understand injection attack mechanics
- Prevent SQL, NoSQL, and command injection
- Apply parameterized queries consistently
- Recognize injection vulnerabilities in code review

## Why This Matters

Injection remains one of the most dangerous and common vulnerabilities. A single injection flaw can compromise your entire database or system.

## The Concept

### How Injection Works

```
User Input: ' OR '1'='1

❌ Vulnerable Query Construction:
query = "SELECT * FROM users WHERE name = '" + input + "'"

Result:
SELECT * FROM users WHERE name = '' OR '1'='1'

→ Returns ALL users!
```

### Types of Injection

| Type | Target | Example |
|------|--------|---------|
| SQL Injection | Relational DB | `'; DROP TABLE users;--` |
| NoSQL Injection | MongoDB, etc. | `{"$gt": ""}` |
| Command Injection | OS commands | `; rm -rf /` |
| LDAP Injection | Directory services | `*)(uid=*))(|(uid=*` |
| XPath Injection | XML queries | `' or '1'='1` |

### SQL Injection Prevention

#### ❌ Vulnerable String Concatenation

```java
String query = "SELECT * FROM users WHERE id = " + userId;
String query = String.format("SELECT * FROM users WHERE id = %s", userId);
```

#### ✅ Parameterized Queries

```java
// JDBC PreparedStatement
PreparedStatement stmt = conn.prepareStatement(
    "SELECT * FROM users WHERE id = ?"
);
stmt.setLong(1, userId);

// Spring Data JPA - Named Parameters
@Query("SELECT u FROM User u WHERE u.id = :id")
User findById(@Param("id") Long id);

// Spring Data JPA - Derived Queries
User findByUsername(String username);  // Auto-parameterized

// Spring JDBC Template
jdbcTemplate.queryForObject(
    "SELECT * FROM users WHERE id = ?",
    new Object[]{userId},
    userRowMapper
);
```

### NoSQL Injection Prevention

```java
// ❌ Vulnerable MongoDB
String query = "{ username: '" + username + "' }";

// ✅ Secure with Spring Data MongoDB
User findByUsername(String username);  // Repository method

// ✅ Secure with Criteria
Criteria.where("username").is(username);
```

### Command Injection Prevention

```java
// ❌ Vulnerable
Runtime.getRuntime().exec("ping " + hostname);

// ✅ Secure - Use ProcessBuilder with list arguments
ProcessBuilder pb = new ProcessBuilder("ping", "-c", "4", hostname);
pb.start();

// ✅ Better - Validate input first
if (!hostname.matches("^[a-zA-Z0-9.-]+$")) {
    throw new IllegalArgumentException("Invalid hostname");
}
```

### Input Validation

Layer defenses: validate AND use parameterized queries.

```java
public User findUser(String username) {
    // Validation layer
    if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("Username required");
    }
    if (!username.matches("^[a-zA-Z0-9_]{3,20}$")) {
        throw new IllegalArgumentException("Invalid username format");
    }
    
    // Parameterized query (still use even with validation)
    return userRepository.findByUsername(username);
}
```

### ORM Doesn't Automatically Protect

```java
// ❌ Still vulnerable with JPA native query
@Query(value = "SELECT * FROM users WHERE name = '" + name + "'", 
       nativeQuery = true)
User findByName(String name);

// ✅ Secure with parameters
@Query(value = "SELECT * FROM users WHERE name = :name", 
       nativeQuery = true)
User findByName(@Param("name") String name);
```

### Code Review Checklist

When reviewing code, look for:

| Red Flag | Secure Alternative |
|----------|-------------------|
| String concatenation in queries | Parameterized queries |
| `String.format` for SQL | Named parameters |
| User input in `exec()` | ProcessBuilder with args |
| Dynamic table/column names | Whitelist validation |

## Summary

- Injection occurs when user data becomes part of commands
- Always use parameterized queries—no exceptions
- JPA repository methods and `@Param` are safe
- Validate input as an additional layer
- Never trust input, even from authenticated users

## Additional Resources

- [OWASP Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Injection_Prevention_Cheat_Sheet.html)
- [OWASP SQL Injection Prevention](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
