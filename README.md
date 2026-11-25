# Java Graph Toolkit

A Java toolkit for creating directed graphs, finding paths, and exporting graph visualizations. Developed as a university software engineering project.

## Features

- Add and remove nodes and directed edges.
- Prevent duplicate nodes and edges.
- Read edge definitions from a text file.
- Find paths using breadth-first search (BFS) and depth-first search (DFS).
- Export graph summaries and Graphviz DOT files.
- Generate graph images using Graphviz.
- Test graph operations with JUnit 5.

## Technologies

Java 25, Maven, JUnit 5, Graphviz

## Getting Started

### Requirements

- JDK 25, as configured in `pom.xml`
- Maven
- Graphviz, with the `dot` command available on your PATH for image generation

### Build and Test

From the repository root:

```bash
mvn test
mvn package
```

### Run the Example

```bash
java -cp target/classes org.example.Main
```

The example creates a directed graph and generates:

- `graph.txt` — graph summary
- `graph.dot` — Graphviz representation
- `graph.png` — graph visualization

## Usage Example

```java
Graph graph = new Graph();

graph.addEdge("A", "B");
graph.addEdge("B", "C");

Path path = graph.GraphSearch(
    new Node("A"),
    new Node("C"),
    Algorithm.BFS
);

System.out.println(path);
```

Expected path:

```text
A -> B -> C
```

## Project Structure

- `Graph.java` — graph operations, traversal, and exports
- `Node.java` — node representation
- `Path.java` — path representation
- `Algorithm.java` — traversal algorithm selection
- `Main.java` — demonstration
- `GraphTest.java` — unit tests
