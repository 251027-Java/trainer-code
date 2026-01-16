# Cypher Query Language

## Learning Objectives

- Write basic Cypher queries for CRUD operations
- Use pattern matching for graph traversal
- Apply filtering and aggregation
- Understand Cypher best practices

## Why This Matters

Cypher is your primary tool for interacting with Neo4j. Mastering Cypher enables you to efficiently query, create, and manipulate graph data.

## The Concept

### Cypher Overview

Cypher uses **pattern matching** to describe what you want to find or create. The syntax mirrors how you'd draw a graph on a whiteboard.

```
(node)-[:RELATIONSHIP]->(node)
```

### CRUD Operations

#### CREATE - Add Data

```cypher
// Create a single node
CREATE (:Person {name: 'Alice', email: 'alice@example.com'})

// Create multiple nodes
CREATE (:Person {name: 'Bob'}),
       (:Person {name: 'Carol'})

// Create with relationship
CREATE (a:Person {name: 'Dave'})-[:KNOWS]->(b:Person {name: 'Eve'})
```

#### MATCH - Query Data

```cypher
// Find all Person nodes
MATCH (p:Person)
RETURN p

// Find with property filter
MATCH (p:Person {name: 'Alice'})
RETURN p

// Find with WHERE clause
MATCH (p:Person)
WHERE p.age > 25
RETURN p.name, p.age
```

#### UPDATE - Modify Data

```cypher
// Update property
MATCH (p:Person {name: 'Alice'})
SET p.email = 'alice.new@example.com'

// Add label
MATCH (p:Person {name: 'Alice'})
SET p:Employee

// Add multiple properties
MATCH (p:Person {name: 'Alice'})
SET p += {department: 'Engineering', startDate: date('2024-01-15')}
```

#### DELETE - Remove Data

```cypher
// Delete a node (must have no relationships)
MATCH (p:Person {name: 'Temp'})
DELETE p

// Delete node and all relationships
MATCH (p:Person {name: 'Temp'})
DETACH DELETE p

// Delete a relationship
MATCH (a:Person)-[r:KNOWS]->(b:Person)
WHERE a.name = 'Alice' AND b.name = 'Bob'
DELETE r
```

### Pattern Matching

#### Relationship Patterns

```cypher
// Outgoing relationship
(a)-[:KNOWS]->(b)

// Incoming relationship
(a)<-[:KNOWS]-(b)

// Either direction
(a)-[:KNOWS]-(b)

// Any relationship type
(a)-[]->(b)

// Multiple relationship types
(a)-[:KNOWS|:WORKS_WITH]->(b)
```

#### Variable-Length Paths

```cypher
// Exactly 2 hops
(a)-[:KNOWS*2]->(b)

// 1 to 3 hops
(a)-[:KNOWS*1..3]->(b)

// Any number of hops
(a)-[:KNOWS*]->(b)

// Optional relationship
OPTIONAL MATCH (a)-[:WORKS_AT]->(c)
```

### Filtering with WHERE

```cypher
// Multiple conditions
MATCH (p:Person)
WHERE p.age > 25 AND p.city = 'NYC'
RETURN p

// String operations
MATCH (p:Person)
WHERE p.name STARTS WITH 'A'
RETURN p

MATCH (p:Person)
WHERE p.name CONTAINS 'ice'
RETURN p

// List operations
MATCH (p:Person)
WHERE p.skill IN ['Java', 'Python']
RETURN p

// Existence check
MATCH (p:Person)
WHERE EXISTS { (p)-[:WORKS_AT]->(:Company) }
RETURN p
```

### Aggregation

```cypher
// Count
MATCH (p:Person)
RETURN count(p) as personCount

// Group by with count
MATCH (p:Person)-[:WORKS_AT]->(c:Company)
RETURN c.name, count(p) as employees
ORDER BY employees DESC

// Collect into list
MATCH (p:Person)-[:KNOWS]->(friend)
RETURN p.name, collect(friend.name) as friends

// Aggregation functions
MATCH (p:Person)
RETURN avg(p.age), min(p.age), max(p.age), sum(p.salary)
```

### Useful Clauses

```cypher
// ORDER BY
MATCH (p:Person)
RETURN p.name
ORDER BY p.name ASC

// LIMIT and SKIP
MATCH (p:Person)
RETURN p.name
SKIP 10 LIMIT 5

// DISTINCT
MATCH (p:Person)-[:KNOWS]->(friend)
RETURN DISTINCT friend.name

// UNION
MATCH (p:Person) WHERE p.age > 30 RETURN p.name
UNION
MATCH (p:Person) WHERE p.city = 'NYC' RETURN p.name
```

### Common Patterns

#### Find Friends of Friends

```cypher
MATCH (me:Person {name: 'Alice'})-[:KNOWS*2]->(foaf:Person)
WHERE me <> foaf
RETURN DISTINCT foaf.name
```

#### Find Shortest Path

```cypher
MATCH path = shortestPath(
  (a:Person {name: 'Alice'})-[:KNOWS*]-(b:Person {name: 'Zach'})
)
RETURN path
```

## Summary

- CREATE, MATCH, SET, DELETE for CRUD operations
- Pattern matching mirrors graph structure
- Variable-length paths with `*n` or `*n..m` syntax
- WHERE for filtering, ORDER BY/LIMIT for result control
- Aggregation: count, collect, avg, sum, min, max

## Additional Resources

- [Cypher Reference Card](https://neo4j.com/docs/cypher-refcard/)
- [Cypher Manual](https://neo4j.com/docs/cypher-manual/)
