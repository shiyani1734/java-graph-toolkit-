package org.example;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph();

        // add nodes
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        // add edges
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        // print graph
        System.out.println(graph);

        // save graph text output
        graph.outputGraph("graph.txt");

        // create DOT file
        graph.outputDOTGraph("graph.dot");

        // create PNG image
        graph.outputGraphics("graph", "png");

    }
}