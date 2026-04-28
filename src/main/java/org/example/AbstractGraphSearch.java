package org.example;

import java.util.*;

public abstract class AbstractGraphSearch {

    public Path search(Node src, Node dst, Graph graph) {
        if (src == null || dst == null) {
            return null;
        }

        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parent = new HashMap<>();

        initializeFrontier(src);
        visited.add(src);

        while (!isFrontierEmpty()) {
            Node current = removeNextNode();

            Path currentPath = buildPath(current, parent);
            System.out.println("visiting " + currentPath);

            if (current.equals(dst)) {
                return currentPath;
            }

            processNeighbors(current, graph, visited, parent);
        }

        return null;
    }

    protected Path buildPath(Node dst, Map<Node, Node> parent) {
        List<Node> pathNodes = new ArrayList<>();
        Node step = dst;

        while (step != null) {
            pathNodes.add(step);
            step = parent.get(step);
        }

        Collections.reverse(pathNodes);
        return new Path(pathNodes);
    }

    protected void processNeighbors(Node current, Graph graph,
                                    Set<Node> visited, Map<Node, Node> parent) {
        List<Node> neighbors = graph.getNeighborsForSearch(current);

        for (Node neighbor : orderNeighbors(neighbors)) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                parent.put(neighbor, current);
                addNodeToFrontier(neighbor);
            }
        }
    }

    protected List<Node> orderNeighbors(List<Node> neighbors) {
        return neighbors;
    }

    protected abstract void initializeFrontier(Node src);

    protected abstract boolean isFrontierEmpty();

    protected abstract Node removeNextNode();

    protected abstract void addNodeToFrontier(Node node);
}