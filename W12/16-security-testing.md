# Security Testing

## Learning Objectives

- Understand types of security testing
- Apply security testing in development
- Use security testing tools
- Integrate security into CI/CD

## Why This Matters

Finding vulnerabilities before attackers do is the goal. Security testing validates that your defenses work and catches issues early when they're cheap to fix.

## The Concept

### Types of Security Testing

| Type | Description | When |
|------|-------------|------|
| **SAST** | Static analysis of code | During development |
| **DAST** | Dynamic testing of running app | After deployment |
| **SCA** | Scan dependencies | During build |
| **Penetration** | Simulated attacks | Before release |
| **Security Unit Tests** | Test security controls | With other tests |

### Security Unit Tests

```java
@SpringBootTest
class SecurityTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void unauthenticatedUserCannotAccessProtected() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void regularUserCannotAccessAdmin() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanAccessAdmin() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isOk());
    }
    
    @Test
    void sqlInjectionPrevented() throws Exception {
        mockMvc.perform(get("/api/users")
            .param("name", "'; DROP TABLE users;--"))
            .andExpect(status().isOk());  // Should not crash
        // Verify table still exists
    }
}
```

### SAST Tools

| Tool | Type | Integration |
|------|------|-------------|
| SpotBugs + Find Security Bugs | Maven plugin | CI/CD |
| SonarQube | Platform | CI/CD |
| Semgrep | CLI | CI/CD, IDE |
| Checkmarx | Commercial | Enterprise |

```xml
<!-- SpotBugs with Security plugin -->
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <version>4.8.0.0</version>
    <configuration>
        <plugins>
            <plugin>
                <groupId>com.h3xstream.findsecbugs</groupId>
                <artifactId>findsecbugs-plugin</artifactId>
                <version>1.12.0</version>
            </plugin>
        </plugins>
    </configuration>
</plugin>
```

### DAST Tools

| Tool | Type | Usage |
|------|------|-------|
| OWASP ZAP | Free | API/Web scanning |
| Burp Suite | Commercial | Manual testing |
| Nuclei | Free | Template-based |

### CI/CD Security Pipeline

```yaml
# .github/workflows/security.yml
name: Security Scan

on: [push, pull_request]

jobs:
  security:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Run SAST (SpotBugs)
        run: mvn spotbugs:check
        
      - name: Run Dependency Check
        run: mvn dependency-check:check
        
      - name: Run OWASP ZAP (DAST)
        uses: zaproxy/action-full-scan@v0.4.0
        with:
          target: 'http://localhost:8080'
```

### Testing Checklist

#### Authentication

- [ ] Login requires valid credentials
- [ ] Brute force protection works
- [ ] Session timeout functions
- [ ] Logout invalidates session

#### Authorization

- [ ] Users can't access others' data
- [ ] Role restrictions enforced
- [ ] Admin functions protected

#### Input Validation

- [ ] SQL injection prevented
- [ ] XSS prevented
- [ ] CSRF tokens required

#### Data Protection

- [ ] HTTPS enforced
- [ ] Sensitive data not logged
- [ ] Passwords properly hashed

## Summary

- Security testing includes SAST, DAST, SCA, and unit tests
- Write unit tests for authentication and authorization
- Use SAST tools (SpotBugs, SonarQube) during builds
- Integrate security scanning into CI/CD pipeline
- Test regularly, not just before release

## Additional Resources

- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [OWASP ZAP](https://www.zaproxy.org/)
- [Find Security Bugs](https://find-sec-bugs.github.io/)
