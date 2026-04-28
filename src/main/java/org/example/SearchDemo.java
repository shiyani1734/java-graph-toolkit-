package org.example;

public class SearchDemo {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.parseGraph("src/main/resources/demoGraph.dot");

        System.out.println("BFS a -> h:");
        System.out.println(graph.GraphSearch("a", "h", Algorithm.BFS));

        System.out.println("DFS a -> h:");
        System.out.println(graph.GraphSearch("a", "h", Algorithm.DFS));

        System.out.println("Random Walk a -> c:");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Run " + i + ": " + graph.GraphSearch("a", "c", Algorithm.RANDOM_WALK));
        }
    }
}