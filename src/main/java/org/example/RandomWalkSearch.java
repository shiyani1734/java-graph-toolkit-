package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalkSearch extends AbstractGraphSearch implements SearchStrategy {
    private List<Node> frontier;
    private Random random;

    public RandomWalkSearch() {
        frontier = new ArrayList<>();
        random = new Random();
    }

    @Override
    protected void initializeFrontier(Node src) {
        frontier.clear();
        frontier.add(src);
    }

    @Override
    protected boolean isFrontierEmpty() {
        return frontier.isEmpty();
    }

    @Override
    protected Node removeNextNode() {
        return frontier.remove(0);
    }

    @Override
    protected void addNodeToFrontier(Node node) {
        frontier.clear();
        frontier.add(node);
    }

    @Override
    protected List<Node> orderNeighbors(List<Node> neighbors) {
        List<Node> ordered = new ArrayList<>();
        if (!neighbors.isEmpty()) {
            Node randomNeighbor = neighbors.get(random.nextInt(neighbors.size()));
            ordered.add(randomNeighbor);
        }
        return ordered;
    }
}