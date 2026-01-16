# Dependency Management and Secure Components

## Learning Objectives

- Understand risks from vulnerable dependencies
- Scan and identify vulnerable components
- Apply secure dependency management practices
- Keep dependencies updated safely

## Why This Matters

Most applications use dozens to hundreds of dependencies. A single vulnerable library can compromise your entire system. Managing this risk is essential.

## The Concept

### The Risk

```
Your Application
    └── Spring Boot
         └── Jackson 2.9.8 (vulnerable CVE-2019-12086)
              └── Attacker exploits deserialization flaw
                   └── Remote code execution!
```

### Dependency Scanning Tools

| Tool | Type | Cost | Integration |
|------|------|------|-------------|
| **Dependabot** | GitHub-native | Free | Automatic PRs |
| **Snyk** | SaaS | Free tier | CI/CD, IDE |
| **OWASP Dependency-Check** | CLI/Plugin | Free | Maven, Gradle |
| **GitHub Security Alerts** | GitHub-native | Free | Automatic alerts |
| **JFrog Xray** | Enterprise | Commercial | Full pipeline |

### OWASP Dependency-Check

```xml
<!-- Maven plugin -->
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>8.4.0</version>
    <configuration>
        <failBuildOnCVSS>7</failBuildOnCVSS>
    </configuration>
</plugin>
```

```bash
mvn dependency-check:check
```

### Dependabot Configuration

```yaml
# .github/dependabot.yml
version: 2
updates:
  - package-ecosystem: "maven"
    directory: "/"
    schedule:
      interval: "weekly"
    open-pull-requests-limit: 10
```

### Best Practices

#### 1. Regular Updates

```bash
# Check for updates
mvn versions:display-dependency-updates

# Update to latest versions
mvn versions:use-latest-releases
```

#### 2. Lock Versions

```xml
<!-- Don't use loose version ranges in production -->
<!-- ❌ Risky -->
<version>[1.0,2.0)</version>

<!-- ✅ Explicit -->
<version>1.5.3</version>
```

#### 3. Review Transitive Dependencies

```bash
mvn dependency:tree
```

#### 4. Minimal Dependencies

Only include what you actually use:

```xml
<!-- ❌ Full Spring Boot Web (includes everything) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- ✅ Or exclude unused transitive dependencies -->
<exclusions>
    <exclusion>
        <groupId>com.unused</groupId>
        <artifactId>unused-library</artifactId>
    </exclusion>
</exclusions>
```

### Responding to Vulnerabilities

```
1. Alert received (CVE-2024-XXXX in library-1.2.3)
         ↓
2. Assess impact (Does your code use affected feature?)
         ↓
3. Check for fix (Is there a patched version?)
         ↓
4. Update dependency
         ↓
5. Test thoroughly
         ↓
6. Deploy
```

### Supply Chain Security

#### Verify Integrity

```xml
<!-- Maven: Verify checksums -->
<configuration>
    <checksums>fail</checksums>
</configuration>
```

#### Use Trusted Repositories

```xml
<repositories>
    <repository>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
    </repository>
    <!-- Avoid untrusted repos -->
</repositories>
```

## Summary

- Vulnerable dependencies are a top security risk
- Use scanning tools: Dependabot, Snyk, OWASP Dependency-Check
- Lock versions explicitly in production
- Review and minimize dependencies
- Respond quickly to vulnerability alerts

## Additional Resources

- [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/)
- [GitHub Dependabot](https://docs.github.com/en/code-security/dependabot)
- [Snyk](https://snyk.io/)
