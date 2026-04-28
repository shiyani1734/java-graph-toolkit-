package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    void testAddNode() {

        System.out.println("Running testAddNode");

        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("A");

        String output = graph.toString();

        System.out.println(output);

        assertTrue(output.contains("Number of nodes: 1"));
    }

    @Test
    void testAddEdge() {

        System.out.println("Running testAddEdge");

        Graph graph = new Graph();
        graph.addEdge("A","B");
        graph.addEdge("A","B");

        String output = graph.toString();

        System.out.println(output);

        assertTrue(output.contains("Number of edges: 1"));
    }

    @Test
    void testAddMultipleNodes() {

        System.out.println("Running testAddMultipleNodes");

        Graph graph = new Graph();
        String[] nodes = {"A","B","C"};

        graph.addNodes(nodes);

        String output = graph.toString();

        System.out.println(output);

        assertTrue(output.contains("Number of nodes: 3"));
    }

    @Test
    void testRemoveNode() {

        System.out.println("Running testRemoveNode");

        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.removeNode("A");

        String output = graph.toString();

        System.out.println(output);

        assertTrue(output.contains("Number of nodes: 2"));
        assertTrue(output.contains("Number of edges: 1"));
        assertTrue(output.contains("B -> C"));
        assertFalse(output.contains("A -> B"));
        assertFalse(output.contains("C -> A"));
    }

    @Test
    void testRemoveMultipleNodes() {

        System.out.println("Running testRemoveMultipleNodes");

        Graph graph = new Graph();

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        String[] nodesToRemove = {"A", "C"};
        graph.removeNodes(nodesToRemove);

        String output = graph.toString();

        System.out.println(output);

        assertTrue(output.contains("Number of nodes: 2"));
        assertTrue(output.contains("Number of edges: 0"));
        assertFalse(output.contains("A -> B"));
        assertFalse(output.contains("B -> C"));
        assertFalse(output.contains("C -> D"));
    }

    @Test
    void testRemoveEdge() {

        System.out.println("Running testRemoveEdge");

        Graph graph = new Graph();

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        graph.removeEdge("C", "A");

        String output = graph.toString();

        System.out.println(output);

        assertTrue(output.contains("Number of nodes: 3"));
        assertTrue(output.contains("Number of edges: 2"));
        assertTrue(output.contains("A -> B"));
        assertTrue(output.contains("B -> C"));
        assertFalse(output.contains("C -> A"));
    }

    @Test
    void testRemoveNodeThatDoesNotExist() {

        System.out.println("Running testRemoveNodeThatDoesNotExist");

        Graph graph = new Graph();
        graph.addNode("A");

        assertThrows(IllegalArgumentException.class, () -> {
            graph.removeNode("Z");
        });
    }

    @Test
    void testRemoveEdgeThatDoesNotExist() {

        System.out.println("Running testRemoveEdgeThatDoesNotExist");

        Graph graph = new Graph();
        graph.addEdge("A", "B");

        assertThrows(IllegalArgumentException.class, () -> {
            graph.removeEdge("B", "C");
        });
    }

    @Test
    void testGraphSearchWithBFS() {

        System.out.println("Running testGraphSearchWithBFS");

        Graph graph = new Graph();

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        Path path = graph.GraphSearch(new Node("A"), new Node("D"), Algorithm.BFS);

        assertNotNull(path);
        assertEquals("A -> B -> C -> D", path.toString());
    }

    @Test
    void testGraphSearchWithDFS() {

        System.out.println("Running testGraphSearchWithDFS");

        Graph graph = new Graph();

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        Path path = graph.GraphSearch(new Node("A"), new Node("D"), Algorithm.DFS);

        assertNotNull(path);
        assertEquals("A -> B -> C -> D", path.toString());
    }
    @Test
    void testGraphSearchWithRandomWalk() {

        System.out.println("Running testGraphSearchWithRandomWalk");

        Graph graph = new Graph();

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");

        Path path = graph.GraphSearch("a", "c", Algorithm.RANDOM_WALK);

        assertNotNull(path);
        assertEquals("a -> b -> c", path.toString());
    }
}