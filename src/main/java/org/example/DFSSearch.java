package org.example;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DFSSearch extends AbstractGraphSearch {
    private Stack<Node> stack;

    @Override
    protected void initializeFrontier(Node src) {
        stack = new Stack<>();
        stack.push(src);
    }

    @Override
    protected boolean isFrontierEmpty() {
        return stack.isEmpty();
    }

    @Override
    protected Node removeNextNode() {
        return stack.pop();
    }

    @Override
    protected void addNodeToFrontier(Node node) {
        stack.push(node);
    }

    @Override
    protected List<Node> orderNeighbors(List<Node> neighbors) {
        Collections.reverse(neighbors);
        return neighbors;
    }
}