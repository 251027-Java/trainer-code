# Instructor Demo: Neo4j and Cypher Basics

## Demo Overview

**Duration:** 40-50 minutes  
**Type:** Live Coding  
**Prerequisites:** Neo4j Desktop or Docker installed

## Materials Needed

- Neo4j Desktop or Docker
- Neo4j Browser
- This guide

---

## Phase 1: Setup

**Time:** 5 minutes

### Opening

"Today we'll explore Neo4j, the most popular graph database. We'll create a social network graph and query it with Cypher."

### Start Neo4j

**Neo4j Desktop:**

1. Open Neo4j Desktop
2. Create new project → Add Database → Local DBMS
3. Start the database
4. Open Neo4j Browser

**Docker:**

```bash
docker run -d --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/password123 \
  neo4j:5

# Open browser at http://localhost:7474
```

---

## Phase 2: Creating Data

**Time:** 10 minutes

### Create People

```cypher
// Create our first person
CREATE (:Person {name: 'Alice', age: 30, city: 'NYC'})

// Create more people
CREATE (:Person {name: 'Bob', age: 25, city: 'NYC'})
CREATE (:Person {name: 'Carol', age: 35, city: 'Boston'})
CREATE (:Person {name: 'Dave', age: 28, city: 'NYC'})
CREATE (:Person {name: 'Eve', age: 32, city: 'Boston'})
```

"Notice the pattern: `(:Label {properties})`"

### View the Data

```cypher
MATCH (p:Person) RETURN p
```

"See how Neo4j Browser visualizes nodes. Click on nodes to see properties."

### Create Relationships

```cypher
// Find Alice and Bob, create relationship
MATCH (a:Person {name: 'Alice'})
MATCH (b:Person {name: 'Bob'})
CREATE (a)-[:KNOWS {since: 2019}]->(b)

// Create more relationships
MATCH (a:Person {name: 'Alice'}), (c:Person {name: 'Carol'})
CREATE (a)-[:KNOWS {since: 2020}]->(c)

MATCH (b:Person {name: 'Bob'}), (d:Person {name: 'Dave'})
CREATE (b)-[:KNOWS {since: 2021}]->(d)

MATCH (c:Person {name: 'Carol'}), (e:Person {name: 'Eve'})
CREATE (c)-[:KNOWS {since: 2018}]->(e)

MATCH (d:Person {name: 'Dave'}), (e:Person {name: 'Eve'})
CREATE (d)-[:KNOWS {since: 2022}]->(e)
```

### View the Graph

```cypher
MATCH (p:Person)-[r:KNOWS]->(other)
RETURN p, r, other
```

"Now we see the connected graph. This is what makes graph databases special."

---

## Phase 3: Querying

**Time:** 15 minutes

### Basic Queries

```cypher
// Who does Alice know?
MATCH (alice:Person {name: 'Alice'})-[:KNOWS]->(friend)
RETURN friend.name

// Who knows Alice?
MATCH (person)-[:KNOWS]->(alice:Person {name: 'Alice'})
RETURN person.name
```

### Friends of Friends

```cypher
// Find friends of friends (2 hops)
MATCH (alice:Person {name: 'Alice'})-[:KNOWS*2]->(foaf)
RETURN DISTINCT foaf.name

// With path details
MATCH path = (alice:Person {name: 'Alice'})-[:KNOWS*2]->(foaf)
RETURN path
```

### Filtering

```cypher
// People in NYC
MATCH (p:Person)
WHERE p.city = 'NYC'
RETURN p.name, p.age

// People Alice knows who are over 30
MATCH (alice:Person {name: 'Alice'})-[:KNOWS]->(friend)
WHERE friend.age > 30
RETURN friend.name, friend.age
```

### Aggregation

```cypher
// Count connections per person
MATCH (p:Person)-[:KNOWS]->(friend)
RETURN p.name, count(friend) as friendCount
ORDER BY friendCount DESC

// Average age by city
MATCH (p:Person)
RETURN p.city, avg(p.age) as avgAge
```

---

## Phase 4: Updates and Deletes

**Time:** 10 minutes

### Update Properties

```cypher
// Update Alice's age
MATCH (a:Person {name: 'Alice'})
SET a.age = 31
RETURN a

// Add new property
MATCH (a:Person {name: 'Alice'})
SET a.email = 'alice@example.com'
```

### Delete

```cypher
// Create temp person
CREATE (:Person {name: 'Temp'})

// Delete (must have no relationships)
MATCH (t:Person {name: 'Temp'})
DELETE t

// Delete with relationships: DETACH DELETE
```

---

## Phase 5: Practical Scenario

**Time:** 10 minutes

### Add Companies

```cypher
CREATE (:Company {name: 'TechCo', industry: 'Technology'})
CREATE (:Company {name: 'FinCo', industry: 'Finance'})

// Connect people to companies
MATCH (a:Person {name: 'Alice'}), (t:Company {name: 'TechCo'})
CREATE (a)-[:WORKS_AT {since: 2020}]->(t)

MATCH (b:Person {name: 'Bob'}), (t:Company {name: 'TechCo'})
CREATE (b)-[:WORKS_AT {since: 2021}]->(t)

MATCH (c:Person {name: 'Carol'}), (f:Company {name: 'FinCo'})
CREATE (c)-[:WORKS_AT {since: 2019}]->(f)
```

### Query Across Relationships

```cypher
// Who works with Alice at the same company?
MATCH (alice:Person {name: 'Alice'})-[:WORKS_AT]->(company)<-[:WORKS_AT]-(colleague)
WHERE alice <> colleague
RETURN colleague.name, company.name
```

---

## Key Takeaways

```
┌─────────────────────────────────────────────────────────────┐
│              NEO4J + CYPHER ESSENTIALS                      │
├─────────────────────────────────────────────────────────────┤
│  CREATE  → Add nodes and relationships                      │
│  MATCH   → Find patterns in the graph                       │
│  SET     → Update properties                                │
│  DELETE  → Remove nodes (DETACH DELETE for relationships)   │
│  RETURN  → Specify what to return                           │
│  WHERE   → Filter results                                   │
└─────────────────────────────────────────────────────────────┘
```

---

## Instructor Notes

### Common Issues

| Issue | Solution |
|-------|----------|
| Can't connect | Verify Neo4j is running, check port 7474 |
| Authentication error | Check username/password in docker command |
| Slow queries | Add indexes for production |
