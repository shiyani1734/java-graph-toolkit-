package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class BFSSearch extends AbstractGraphSearch {
    private Queue<Node> queue;

    @Override
    protected void initializeFrontier(Node src) {
        queue = new LinkedList<>();
        queue.offer(src);
    }

    @Override
    protected boolean isFrontierEmpty() {
        return queue.isEmpty();
    }

    @Override
    protected Node removeNextNode() {
        return queue.poll();
    }

    @Override
    protected void addNodeToFrontier(Node node) {
        queue.offer(node);
    }
}