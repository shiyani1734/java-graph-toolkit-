package org.example;

public interface SearchStrategy {
    Path search(Node src, Node dst, Graph graph);
}