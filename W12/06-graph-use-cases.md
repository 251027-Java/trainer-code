# Graph Database Use Cases

## Learning Objectives

- Identify real-world applications of graph databases
- Recognize patterns where graphs outperform relational databases
- Apply graph thinking to business problems
- Design graph solutions for common scenarios

## Why This Matters

Knowing when to use a graph database is as important as knowing how. These use cases help you recognize opportunities to apply graph technology effectively.

## The Concept

### Use Case Categories

| Category | Example Applications |
|----------|---------------------|
| Social Networks | Connections, recommendations |
| Knowledge Graphs | Semantic search, Q&A systems |
| Fraud Detection | Pattern recognition, anomaly detection |
| Supply Chain | Logistics, dependency tracking |
| IT Operations | Infrastructure mapping |
| Life Sciences | Drug discovery, genomics |

---

### Use Case 1: Social Network

**Problem:** Find friends, friends-of-friends, mutual connections

**Graph Model:**

```cypher
(:User)-[:FOLLOWS]->(:User)
(:User)-[:LIKES]->(:Post)
(:User)-[:MEMBER_OF]->(:Group)
```

**Sample Queries:**

```cypher
// Mutual friends
MATCH (me:User {id: 'alice'})-[:FOLLOWS]->(mutual)<-[:FOLLOWS]-(other:User {id: 'bob'})
RETURN mutual

// Recommended connections (friends of friends not yet followed)
MATCH (me:User {id: 'alice'})-[:FOLLOWS*2]->(suggested)
WHERE NOT (me)-[:FOLLOWS]->(suggested) AND me <> suggested
RETURN suggested, count(*) as mutualCount
ORDER BY mutualCount DESC
```

---

### Use Case 2: Recommendation Engine

**Problem:** "Customers who bought X also bought Y"

**Graph Model:**

```cypher
(:Customer)-[:PURCHASED]->(:Product)
(:Product)-[:IN_CATEGORY]->(:Category)
(:Customer)-[:SIMILAR_TO]->(:Customer)
```

**Sample Query:**

```cypher
// Products frequently bought together
MATCH (p1:Product {id: 'product-123'})<-[:PURCHASED]-(:Customer)-[:PURCHASED]->(p2:Product)
WHERE p1 <> p2
RETURN p2.name, count(*) as frequency
ORDER BY frequency DESC LIMIT 5
```

---

### Use Case 3: Fraud Detection

**Problem:** Detect suspicious patterns in financial transactions

**Graph Model:**

```cypher
(:Account)-[:TRANSFERS_TO]->(:Account)
(:Account)-[:OWNED_BY]->(:Person)
(:Person)-[:SHARES_ADDRESS_WITH]->(:Person)
```

**Sample Queries:**

```cypher
// Circular transaction pattern (money laundering indicator)
MATCH path = (a:Account)-[:TRANSFERS_TO*3..6]->(a)
RETURN path

// Accounts connected to known fraudsters
MATCH (fraudster:Person {flagged: true})-[:OWNS]->(account)
       -[:TRANSFERS_TO*1..3]->(:Account)<-[:OWNS]-(suspect)
RETURN DISTINCT suspect
```

---

### Use Case 4: Knowledge Graph

**Problem:** Semantic search and intelligent Q&A

**Graph Model:**

```cypher
(:Concept)-[:RELATED_TO]->(:Concept)
(:Concept)-[:IS_A]->(:Category)
(:Document)-[:MENTIONS]->(:Concept)
```

**Sample Query:**

```cypher
// Find documents about related topics
MATCH (topic:Concept {name: 'Machine Learning'})-[:RELATED_TO*1..2]->(related)
MATCH (doc:Document)-[:MENTIONS]->(related)
RETURN doc.title, collect(related.name) as topics
```

---

### Use Case 5: Supply Chain / Dependencies

**Problem:** Track component dependencies, impact analysis

**Graph Model:**

```cypher
(:Component)-[:DEPENDS_ON]->(:Component)
(:Component)-[:SUPPLIED_BY]->(:Supplier)
(:Component)-[:USED_IN]->(:Product)
```

**Sample Queries:**

```cypher
// Impact of supplier issue
MATCH (s:Supplier {name: 'SupplierX'})<-[:SUPPLIED_BY]-(part)
       <-[:DEPENDS_ON*]-(affected)
RETURN affected.name

// Critical path dependencies
MATCH (product:Product {name: 'ProductA'})<-[:USED_IN]-()
       -[:DEPENDS_ON*]->(critical)
RETURN critical, count(*) as dependents
ORDER BY dependents DESC
```

---

### Use Case 6: Identity and Access Management

**Problem:** Who has access to what, through which paths?

**Graph Model:**

```cypher
(:User)-[:MEMBER_OF]->(:Group)
(:Group)-[:HAS_ROLE]->(:Role)
(:Role)-[:GRANTS]->(:Permission)
(:Permission)-[:ON]->(:Resource)
```

**Sample Query:**

```cypher
// All resources a user can access
MATCH (u:User {id: 'alice'})-[:MEMBER_OF*0..]->()-[:HAS_ROLE]->()
      -[:GRANTS]->()-[:ON]->(resource)
RETURN DISTINCT resource.name
```

---

### When Graphs Win

| Scenario | Graph Advantage |
|----------|-----------------|
| Many JOINs | Graph traversal is O(1) per hop |
| Relationship queries | First-class citizens |
| Flexible schema | Easy to add new relationship types |
| Path finding | Built-in algorithms |
| Pattern matching | Intuitive query syntax |

## Summary

- Social networks: connections, recommendations
- E-commerce: product recommendations, cross-selling
- Finance: fraud detection, risk analysis
- Knowledge: semantic search, Q&A
- Operations: dependencies, impact analysis
- Security: access paths, permission tracing

## Additional Resources

- [Neo4j Use Cases](https://neo4j.com/use-cases/)
- [Graph Database Use Cases Book](https://neo4j.com/graph-databases-book/)
