# Spring Data Neo4j

## Learning Objectives

- Integrate Neo4j with Spring Boot applications
- Define node and relationship entities
- Create repositories for graph operations
- Execute custom Cypher queries

## Why This Matters

Spring Data Neo4j brings the familiar Spring Data patterns to graph databases. This lets you use Neo4j with the same patterns you know from Spring Data JPA.

## The Concept

### Spring Data Neo4j Overview

```
┌─────────────────────────────────────────────────────────────┐
│              SPRING DATA NEO4J STACK                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Your Application                                           │
│         ↓                                                   │
│  Spring Data Repositories                                   │
│         ↓                                                   │
│  Spring Data Neo4j (OGM)                                    │
│         ↓                                                   │
│  Neo4j Java Driver                                          │
│         ↓                                                   │
│  Neo4j Database                                             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Setup

#### Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-neo4j</artifactId>
</dependency>
```

#### Configuration

```yaml
spring:
  neo4j:
    uri: bolt://localhost:7687
    authentication:
      username: neo4j
      password: password123
```

### Node Entities

```java
import org.springframework.data.neo4j.core.schema.*;

@Node("Person")
public class Person {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private Integer age;
    private String email;
    
    @Relationship(type = "WORKS_AT", direction = Direction.OUTGOING)
    private Company company;
    
    @Relationship(type = "KNOWS")
    private List<Person> friends = new ArrayList<>();
    
    // Constructors, getters, setters
}

@Node("Company")
public class Company {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private Integer founded;
    
    @Relationship(type = "WORKS_AT", direction = Direction.INCOMING)
    private List<Person> employees = new ArrayList<>();
}
```

### Relationship Properties

When relationships have properties, create a relationship entity:

```java
@RelationshipProperties
public class Employment {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private LocalDate startDate;
    private String title;
    
    @TargetNode
    private Company company;
}

@Node("Person")
public class Person {
    
    @Relationship(type = "WORKS_AT")
    private List<Employment> employments;
}
```

### Repository

```java
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    
    // Derived query methods
    Optional<Person> findByName(String name);
    
    List<Person> findByAgeGreaterThan(int age);
    
    // Custom Cypher query
    @Query("MATCH (p:Person)-[:WORKS_AT]->(c:Company {name: $companyName}) RETURN p")
    List<Person> findByCompanyName(String companyName);
    
    @Query("MATCH (p:Person)-[:KNOWS*2]->(foaf:Person) WHERE p.name = $name RETURN DISTINCT foaf")
    List<Person> findFriendsOfFriends(String name);
}
```

### Service Layer

```java
@Service
@Transactional
public class PersonService {
    
    private final PersonRepository personRepository;
    
    public Person create(String name, int age) {
        Person person = new Person(name, age);
        return personRepository.save(person);
    }
    
    public void addFriend(Long personId, Long friendId) {
        Person person = personRepository.findById(personId)
            .orElseThrow();
        Person friend = personRepository.findById(friendId)
            .orElseThrow();
        
        person.getFriends().add(friend);
        personRepository.save(person);
    }
    
    public List<Person> findFriendsOfFriends(String name) {
        return personRepository.findFriendsOfFriends(name);
    }
}
```

### Key Annotations

| Annotation | Purpose |
|------------|---------|
| `@Node` | Marks class as a graph node |
| `@Id` | Primary identifier |
| `@GeneratedValue` | Auto-generate ID |
| `@Relationship` | Defines relationship |
| `@RelationshipProperties` | Properties on relationships |
| `@TargetNode` | Target node in relationship entity |
| `@Query` | Custom Cypher query |

## Summary

- Spring Data Neo4j integrates Neo4j with Spring Boot patterns
- Use `@Node` for entities, `@Relationship` for connections
- Repositories extend `Neo4jRepository` with derived and custom queries
- Relationship properties require `@RelationshipProperties` classes
- Familiar Spring Data patterns: save, findById, custom queries

## Additional Resources

- [Spring Data Neo4j Reference](https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/)
- [Neo4j OGM Guide](https://neo4j.com/docs/ogm-manual/)
