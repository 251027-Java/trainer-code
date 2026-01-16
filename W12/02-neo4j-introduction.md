# Neo4j Introduction

## Learning Objectives

- Understand what Neo4j is and its architecture
- Set up Neo4j on your system
- Navigate Neo4j Browser
- Understand Neo4j's query language (Cypher)

## Why This Matters

Neo4j is the most widely used graph database. Learning Neo4j gives you practical skills applicable across industries and prepares you for real-world graph database projects.

## The Concept

### What is Neo4j?

**Neo4j** is a native graph database that stores and processes data as a graph. It's designed from the ground up for graph operations, not adapted from relational storage.

```
┌─────────────────────────────────────────────────────────────┐
│                    NEO4J STACK                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Neo4j Browser (UI)                      │   │
│  └─────────────────────────────────────────────────────┘   │
│                          ↓                                  │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Cypher Query Language                   │   │
│  └─────────────────────────────────────────────────────┘   │
│                          ↓                                  │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Neo4j Database Engine                   │   │
│  └─────────────────────────────────────────────────────┘   │
│                          ↓                                  │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Native Graph Storage                    │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Neo4j Editions

| Edition | Use Case | Cost |
|---------|----------|------|
| **Community** | Learning, development, small projects | Free |
| **Enterprise** | Production, clustering, advanced security | Commercial |
| **AuraDB** | Cloud-hosted, fully managed | Pay-as-you-go |

### Installation Options

#### Option 1: Neo4j Desktop (Recommended for Learning)

1. Download from [neo4j.com/download](https://neo4j.com/download/)
2. Install Neo4j Desktop
3. Create a new project
4. Add a local database
5. Start the database

#### Option 2: Docker

```bash
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/password123 \
  neo4j:5
```

#### Option 3: AuraDB Free Tier

1. Sign up at [neo4j.com/cloud/aura](https://neo4j.com/cloud/aura/)
2. Create free instance
3. Connect via Neo4j Browser

### Neo4j Browser

The built-in web interface at `http://localhost:7474`:

| Feature | Description |
|---------|-------------|
| Query Editor | Write and execute Cypher |
| Visualization | See graph results visually |
| Database Info | Schema, indexes, constraints |
| Favorites | Save common queries |
| Guides | Built-in tutorials |

### Cypher Query Language

Cypher is Neo4j's declarative query language. It uses ASCII-art patterns to describe graph patterns.

#### Basic Syntax

```cypher
// Create a node
CREATE (:Person {name: 'Alice', age: 30})

// Create a relationship
MATCH (a:Person {name: 'Alice'})
MATCH (b:Person {name: 'Bob'})
CREATE (a)-[:KNOWS]->(b)

// Query nodes
MATCH (p:Person)
RETURN p.name, p.age

// Query with relationship
MATCH (p:Person)-[:KNOWS]->(friend:Person)
RETURN p.name, friend.name
```

### Cypher Pattern Syntax

```
(:Label)                    // Node with label
(:Label {prop: value})      // Node with property
()-[:TYPE]->()              // Relationship with type
()-[:TYPE {prop: value}]->()// Relationship with property
(a)-[r]->(b)                // Bound to variables
```

### First Query Example

```cypher
// Create sample data
CREATE (alice:Person {name: 'Alice', age: 30})
CREATE (bob:Person {name: 'Bob', age: 25})
CREATE (company:Company {name: 'TechCo'})
CREATE (alice)-[:KNOWS {since: 2019}]->(bob)
CREATE (alice)-[:WORKS_AT {since: 2020}]->(company)
CREATE (bob)-[:WORKS_AT {since: 2021}]->(company)

// Query: Who knows someone who works at TechCo?
MATCH (person:Person)-[:KNOWS]->(friend:Person)
      -[:WORKS_AT]->(c:Company {name: 'TechCo'})
RETURN person.name, friend.name
```

## Summary

- Neo4j is the leading native graph database
- Available as Desktop, Docker, or cloud (AuraDB)
- Neo4j Browser provides visual query interface
- Cypher is the query language using pattern matching
- Patterns use ASCII-art: `()-[:REL]->()` for relationships

## Additional Resources

- [Neo4j Download](https://neo4j.com/download/)
- [Cypher Manual](https://neo4j.com/docs/cypher-manual/)
- [Neo4j GraphAcademy](https://graphacademy.neo4j.com/) (free courses)
