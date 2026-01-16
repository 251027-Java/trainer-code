# Introduction to Graph Databases

## Learning Objectives

- Understand what graph databases are
- Recognize when graphs are better than relational databases
- Identify graph database concepts: nodes, relationships, properties
- Compare popular graph database options

## Why This Matters

Some data is inherently connected—social networks, recommendation systems, fraud detection. Graph databases model these connections naturally, making queries about relationships fast and intuitive.

## The Concept

### What is a Graph Database?

A **graph database** stores data as nodes (entities) connected by relationships (edges), with properties on both.

```
┌─────────────────────────────────────────────────────────────┐
│                  GRAPH DATABASE STRUCTURE                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│    ┌──────────┐         WORKS_AT          ┌──────────┐     │
│    │  Person  │ ─────────────────────────→│ Company  │     │
│    │  "Alice" │        since: 2020        │ "TechCo" │     │
│    └──────────┘                           └──────────┘     │
│         │                                      ↑           │
│         │ KNOWS                                │           │
│         ↓               WORKS_AT               │           │
│    ┌──────────┐ ───────────────────────────────┘           │
│    │  Person  │                                            │
│    │  "Bob"   │                                            │
│    └──────────┘                                            │
│                                                             │
│  NODE = Circle with label                                  │
│  RELATIONSHIP = Arrow with type                            │
│  PROPERTY = Attributes on nodes/relationships              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Graph vs Relational

| Aspect | Relational (SQL) | Graph |
|--------|------------------|-------|
| Structure | Tables with rows | Nodes with relationships |
| Relationships | Foreign keys, JOINs | First-class citizens |
| Schema | Fixed schema | Flexible schema |
| Query style | JOIN-heavy for connections | Traversal-based |
| Best for | Transactional data | Connected data |

### When to Use Graphs

| Use Case | Why Graph Works |
|----------|-----------------|
| Social networks | Natural friend-of-friend queries |
| Recommendations | Find similar users/products easily |
| Fraud detection | Trace money/connection patterns |
| Knowledge graphs | Model complex domain relationships |
| Network/IT ops | Map infrastructure dependencies |
| Supply chain | Track component relationships |

### Core Concepts

#### Nodes

Entities in your domain:

```
(:Person {name: "Alice", age: 30})
(:Company {name: "TechCo", founded: 2010})
(:Product {sku: "ABC123", price: 29.99})
```

#### Relationships

Connect nodes with meaning:

```
(alice)-[:WORKS_AT {since: 2020}]->(techco)
(alice)-[:PURCHASED {date: "2024-01-15"}]->(product)
(alice)-[:KNOWS {since: 2019}]->(bob)
```

#### Properties

Key-value pairs on nodes and relationships:

- Node properties: name, email, joinDate
- Relationship properties: since, weight, rating

### Popular Graph Databases

| Database | Type | Best For |
|----------|------|----------|
| **Neo4j** | Native graph | General purpose, most popular |
| **Amazon Neptune** | Cloud managed | AWS ecosystem |
| **Azure Cosmos DB** | Multi-model | Azure ecosystem |
| **ArangoDB** | Multi-model | Flexibility |
| **JanusGraph** | Distributed | Very large graphs |

### Comparison: SQL vs Graph Query

**Find friends of friends who work at the same company:**

**SQL (complex JOINs):**

```sql
SELECT DISTINCT p3.name
FROM persons p1
JOIN friendships f1 ON p1.id = f1.person_id
JOIN persons p2 ON f1.friend_id = p2.id
JOIN friendships f2 ON p2.id = f2.person_id
JOIN persons p3 ON f2.friend_id = p3.id
JOIN employment e1 ON p1.id = e1.person_id
JOIN employment e3 ON p3.id = e3.person_id
WHERE p1.name = 'Alice'
  AND e1.company_id = e3.company_id
  AND p3.id != p1.id;
```

**Graph (intuitive traversal):**

```cypher
MATCH (alice:Person {name: 'Alice'})-[:KNOWS*2]->(foaf:Person),
      (alice)-[:WORKS_AT]->(company)<-[:WORKS_AT]-(foaf)
WHERE alice <> foaf
RETURN DISTINCT foaf.name
```

## Summary

- Graph databases store nodes connected by relationships
- Best for inherently connected data: social, recommendations, fraud
- Nodes = entities, Relationships = connections, Properties = attributes
- Neo4j is the most popular graph database
- Graph queries are intuitive for relationship-heavy questions

## Additional Resources

- [Neo4j Getting Started](https://neo4j.com/docs/getting-started/)
- [Graph Databases Book (free)](https://neo4j.com/graph-databases-book/)
