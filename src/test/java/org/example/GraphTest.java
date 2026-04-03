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
}