# Graph Modeling

## Learning Objectives

- Design effective graph data models
- Decide what should be nodes vs relationships vs properties
- Apply modeling best practices
- Refactor models for query patterns

## Why This Matters

A good graph model makes queries simple and fast. A poor model leads to complex queries and poor performance. The initial design decisions have lasting impact.

## The Concept

### Modeling Questions

When designing a graph model, ask:

1. **What are the main entities?** → These become node labels
2. **How are entities connected?** → These become relationships
3. **What describes entities/connections?** → These become properties

### Nodes vs Relationships vs Properties

```
┌─────────────────────────────────────────────────────────────┐
│                   MODELING DECISIONS                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  NODES                                                      │
│  • Things that exist independently                          │
│  • Entities you'll query for                                │
│  • Example: Person, Company, Product                        │
│                                                             │
│  RELATIONSHIPS                                              │
│  • How nodes connect                                        │
│  • Verbs or actions                                         │
│  • Example: KNOWS, WORKS_AT, PURCHASED                      │
│                                                             │
│  PROPERTIES                                                 │
│  • Attributes of nodes or relationships                     │
│  • Simple values (not entities themselves)                  │
│  • Example: name, price, since                              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Decision Guide

| Should Be... | If... |
|--------------|-------|
| **Node** | You need to query it directly |
| **Node** | It connects to multiple other things |
| **Node** | It has many properties |
| **Relationship** | It describes how two things connect |
| **Property** | It's a simple attribute |
| **Property** | You don't query it independently |

### Example: E-Commerce Model

```
(:Customer)-[:PLACED]->(:Order)-[:CONTAINS]->(:Product)
                         ↑
(:Customer)-[:LIVES_IN]->(:City)<-[:LOCATED_IN]-(:Warehouse)
                                                    ↓
(:Product)<-[:STOCKS]-(:Warehouse)
```

**Nodes:** Customer, Order, Product, City, Warehouse  
**Relationships:** PLACED, CONTAINS, LIVES_IN, LOCATED_IN, STOCKS

### Modeling Anti-Patterns

#### ❌ Node as Property

**Wrong:**

```cypher
CREATE (:Person {name: 'Alice', company: 'TechCo'})
```

**Right:**

```cypher
CREATE (a:Person {name: 'Alice'})
CREATE (c:Company {name: 'TechCo'})
CREATE (a)-[:WORKS_AT]->(c)
```

**Why:** Can't query "all employees of TechCo" efficiently.

#### ❌ Relationship as Node

**Wrong (usually):**

```cypher
CREATE (a:Person)-[:PARTICIPATES]->(m:Membership)-[:OF]->(club:Club)
```

**Right (usually):**

```cypher
CREATE (a:Person)-[:MEMBER_OF {since: date('2024-01-01')}]->(club:Club)
```

**Exception:** Use relationship-as-node when you need to attach relationships to the "membership" itself.

### Relationship Direction

Relationships have direction, but you can query in either direction:

```cypher
// Create: direction matters semantically
(alice)-[:FOLLOWS]->(bob)  // Alice follows Bob

// Query: can traverse either direction
MATCH (bob)<-[:FOLLOWS]-(follower) RETURN follower  // Who follows Bob
MATCH (alice)-[:FOLLOWS]->(following) RETURN following  // Who Alice follows
```

### Querying Influences Modeling

**Query:** "Find products frequently bought together"

**Model to support it:**

```cypher
(:Product)-[:OFTEN_BOUGHT_WITH {frequency: 50}]->(:Product)
// or
(:Product)<-[:CONTAINS]-(:Order)-[:CONTAINS]->(:Product)
```

The second is flexible; the first is optimized for that specific query.

### Best Practices

| Practice | Reason |
|----------|--------|
| Use specific labels | `:User` better than `:Node` |
| Use verb relationships | `:FOLLOWS` better than `:FOLLOWER` |
| Keep properties simple | Avoid nested structures |
| Model for your queries | Optimize common access patterns |
| Start simple | Refactor as you learn more |

## Summary

- Entities → Nodes, Connections → Relationships, Attributes → Properties
- Use nodes for things you query directly
- Use relationships for how things connect
- Avoid embedding entities as properties
- Model based on your query patterns
- Start simple and refactor based on actual usage

## Additional Resources

- [Neo4j Modeling Tips](https://neo4j.com/developer/guide-data-modeling/)
- [Graph Modeling Guidelines](https://neo4j.com/docs/getting-started/data-modeling/guide-data-modeling/)
