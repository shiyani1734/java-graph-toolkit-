package org.example;

import java.util.*;

public abstract class AbstractGraphSearch {

    // Template method: defines the fixed search algorithm structure.
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

    // Shared helper for building the final or current path.
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

    // Shared neighbor-processing step used by all search algorithms.
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

    // Hook method: subclasses can override neighbor order if needed.
    protected List<Node> orderNeighbors(List<Node> neighbors) {
        return neighbors;
    }

    // Subclass step: define how the search frontier starts.
    protected abstract void initializeFrontier(Node src);

    // Subclass step: define when the frontier is empty.
    protected abstract boolean isFrontierEmpty();

    // Subclass step: define how the next node is selected.
    protected abstract Node removeNextNode();

    // Subclass step: define how a node is added to the frontier.
    protected abstract void addNodeToFrontier(Node node);
}