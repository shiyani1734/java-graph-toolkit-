package org.example;

import java.util.*;

public class Graph {
    // store nodes
    private Set<String> nodes;

    // store edges
    private List<String[]> edges;

    // constructor
    public Graph() {
        nodes = new HashSet<>();
        edges = new ArrayList<>();
    }

    // add a single node
    public void addNode(String label) {
        if (!nodes.contains(label)) {
            nodes.add(label);
        }
    }

    // add multiple nodes
    public void addNodes(String[] label) {
        for (String s : label) {
            addNode(s);
        }
    }

    // add edge to graph
    public void addEdge(String srcLabel, String dstLabel) {
        addNode(srcLabel);
        addNode(dstLabel);

        for (String[] edge : edges) {
            if (edge[0].equals(srcLabel) && edge[1].equals(dstLabel)) {
                return;
            }
        }

        edges.add(new String[]{srcLabel, dstLabel});
    }

    public void removeNode(String label) {
        if (!nodes.contains(label)) {
            throw new IllegalArgumentException("Node does not exist: " + label);
        }

        nodes.remove(label);
        edges.removeIf(edge -> edge[0].equals(label) || edge[1].equals(label));
    }

    public void removeNodes(String[] label) {
        for (String s : label) {
            removeNode(s);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        boolean removed = edges.removeIf(edge ->
                edge[0].equals(srcLabel) && edge[1].equals(dstLabel));

        if (!removed) {
            throw new IllegalArgumentException("Edge does not exist: " + srcLabel + " -> " + dstLabel);
        }
    }

    public void parseGraph(String filepath) {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filepath));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.contains("->")) {
                    String[] parts = line.replace(";", "").split("->");

                    String src = parts[0].trim();
                    String dst = parts[1].trim();

                    addEdge(src, dst);
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isInvalidSearchInput(Node src, Node dst) {
        return src == null || dst == null;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        for (String[] edge : edges) {
            if (edge[0].equals(node.getLabel())) {
                neighbors.add(new Node(edge[1]));
            }
        }

        return neighbors;
    }

    private Path buildPath(Node dst, Map<Node, Node> parent) {
        List<Node> pathNodes = new ArrayList<>();
        Node step = dst;

        while (step != null) {
            pathNodes.add(step);
            step = parent.get(step);
        }

        Collections.reverse(pathNodes);
        return new Path(pathNodes);
    }

    private Path searchWithBFS(Node src, Node dst) {
        if (isInvalidSearchInput(src, dst)) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parent = new HashMap<>();

        visited.add(src);
        queue.offer(src);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(dst)) {
                return buildPath(dst, parent);
            }

            for (Node neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        return null;
    }

    private Path searchWithDFS(Node src, Node dst) {
        if (isInvalidSearchInput(src, dst)) {
            return null;
        }

        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parent = new HashMap<>();

        stack.push(src);

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);

                if (current.equals(dst)) {
                    return buildPath(dst, parent);
                }

                List<Node> neighbors = getNeighbors(current);

                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    Node neighbor = neighbors.get(i);

                    if (!visited.contains(neighbor)) {
                        if (!parent.containsKey(neighbor)) {
                            parent.put(neighbor, current);
                        }
                        stack.push(neighbor);
                    }
                }
            }
        }

        return null;
    }

    public Path GraphSearch(Node src, Node dst, Algorithm algo) {
        if (algo == Algorithm.BFS) {
            return searchWithBFS(src, dst);
        } else if (algo == Algorithm.DFS) {
            return searchWithDFS(src, dst);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Number of nodes: ").append(nodes.size()).append("\n");

        sb.append("Node labels: ");
        for (String node : nodes) {
            sb.append(node).append(" ");
        }
        sb.append("\n");

        sb.append("Number of edges: ").append(edges.size()).append("\n");

        sb.append("Edges:\n");
        for (String[] edge : edges) {
            sb.append(edge[0]).append(" -> ").append(edge[1]).append("\n");
        }

        return sb.toString();
    }

    public void outputGraph(String filepath) {
        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(filepath));
            writer.write(this.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outputDOTGraph(String path) {
        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(path));

            writer.write("digraph G {\n");

            for (String[] edge : edges) {
                writer.write("    " + edge[0] + " -> " + edge[1] + ";\n");
            }

            writer.write("}\n");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outputGraphics(String path, String format) {
        try {
            String dotFile = path + ".dot";
            outputDOTGraph(dotFile);

            String outputFile = path + "." + format;

            ProcessBuilder pb = new ProcessBuilder(
                    "dot",
                    "-T" + format,
                    dotFile,
                    "-o",
                    outputFile
            );

            pb.start().waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}