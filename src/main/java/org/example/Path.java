package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class Path {
    private List<Node> nodes;

    public Path(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return nodes.stream()
                .map(Node::getLabel)
                .collect(Collectors.joining(" -> "));
    }
}