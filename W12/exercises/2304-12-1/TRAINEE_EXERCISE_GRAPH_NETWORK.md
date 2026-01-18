# Trainee Exercise: Build a Social Network Graph

## Overview

**Duration:** 2-3 hours  
**Type:** Individual Implementation  
**Mode:** Practical Application

## Learning Objectives

- Design a graph data model
- Write Cypher queries for common patterns
- Implement Spring Data Neo4j integration
- Build a REST API backed by graph database

## Prerequisites

- Neo4j installed (Desktop or Docker)
- Spring Boot project knowledge
- Completed graph database readings

## The Challenge

Build a **Professional Network** application (like a simple LinkedIn) using Neo4j.

### Feature Requirements

1. Users can create profiles
2. Users can connect with other users
3. Users can be associated with companies
4. Find "People You May Know" (friends of friends)
5. Find professionals at a specific company
6. Recommend connections based on mutual connections

---

## Instructions

### Part 1: Graph Model Design (30 minutes)

#### Task 1.1: Identify Entities

What entities (nodes) do you need?

| Entity | Properties |
|--------|------------|
| Person | ? |
| Company | ? |
| Skill | ? |

#### Task 1.2: Identify Relationships

What relationships connect your entities?

| Relationship | From | To | Properties |
|--------------|------|-----|------------|
| CONNECTS | Person | Person | since, how_met |
| WORKS_AT | ? | ? | ? |
| HAS_SKILL | ? | ? | ? |

#### Task 1.3: Draw Your Model

Create a diagram showing nodes and relationships.

---

### Part 2: Cypher Implementation (1 hour)

Create these in Neo4j Browser.

#### Task 2.1: Create Sample Data

```cypher
// Create at least 6 people with name, title, city
CREATE (:Person {name: 'Your Name', title: 'Developer', city: 'NYC'})
// ... add more

// Create at least 2 companies
CREATE (:Company {name: 'TechCorp', industry: 'Technology'})
// ... add more

// Create at least 3 skills
CREATE (:Skill {name: 'Java'})
// ... add more
```

#### Task 2.2: Create Relationships

Connect people to:

- Other people (CONNECTS)
- Companies (WORKS_AT)
- Skills (HAS_SKILL)

#### Task 2.3: Write Queries

Implement these queries:

**Query 1:** Find all of a person's direct connections

```cypher
// Your query here
```

**Query 2:** Find friends of friends (potential connections)

```cypher
// Your query here
```

**Query 3:** Find people who share the same skills as you

```cypher
// Your query here
```

**Query 4:** Find all people at a specific company

```cypher
// Your query here
```

**Query 5:** Count connections per person

```cypher
// Your query here
```

---

### Part 3: Spring Data Neo4j Integration (1 hour)

#### Task 3.1: Project Setup

Create Spring Boot project with dependencies:

- Spring Data Neo4j
- Spring Web

Configure application.yml:

```yaml
spring:
  neo4j:
    uri: bolt://localhost:7687
    authentication:
      username: neo4j
      password: your-password
```

#### Task 3.2: Create Entity Classes

```java
// Person.java
@Node("Person")
public class Person {
    @Id @GeneratedValue
    private Long id;
    
    private String name;
    private String title;
    private String city;
    
    @Relationship(type = "CONNECTS")
    private List<Person> connections;
    
    // Add WORKS_AT and HAS_SKILL relationships
}
```

#### Task 3.3: Create Repository

```java
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    
    Optional<Person> findByName(String name);
    
    @Query("MATCH (p:Person)-[:CONNECTS]->(connection) WHERE p.name = $name RETURN connection")
    List<Person> findConnections(String name);
    
    // Add query for friends of friends
    
    // Add query for people with same skills
}
```

#### Task 3.4: Create REST Controller

Create endpoints for:

- GET /api/people - List all people
- GET /api/people/{name}/connections - Get person's connections
- POST /api/people - Create new person
- POST /api/people/{name}/connect/{otherName} - Create connection

---

## Deliverable

Submit a ZIP containing:

1. **Cypher script file** - All your Cypher queries
2. **Spring Boot source code** - Complete project
3. **Screenshots** - Graph visualization from Neo4j Browser
4. **README.md** with:
   - Graph model diagram/description
   - API documentation
   - Example requests/responses

---

## Grading Criteria

| Criteria | Points |
|----------|--------|
| Graph model design | 20 |
| Cypher queries (5 required) | 25 |
| Spring Data Neo4j entities | 15 |
| Repository methods | 15 |
| REST API implementation | 15 |
| Documentation | 10 |
| **Total** | **100** |

---

## Bonus Challenges (+15 points)

1. **Mutual Connections (+5)**: Show how many mutual connections two people have
2. **Connection Path (+5)**: Find shortest path between two people
3. **Skill Recommendations (+5)**: Recommend skills based on what connections have
