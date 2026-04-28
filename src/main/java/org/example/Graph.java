package org.example;

import java.util.*;

public class Graph {
    private Set<String> nodes;
    private List<String[]> edges;

    public Graph() {
        nodes = new HashSet<>();
        edges = new ArrayList<>();
    }

    public void addNode(String label) {
        if (!nodes.contains(label)) {
            nodes.add(label);
        }
    }

    public void addNodes(String[] label) {
        for (String s : label) {
            addNode(s);
        }
    }

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

    private Node getNodeByLabel(String label) {
        if (label == null || !nodes.contains(label)) {
            return null;
        }
        return new Node(label);
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

    public List<Node> getNeighborsForSearch(Node node) {
        return getNeighbors(node);
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

    public Path GraphSearch(String src, String dst, Algorithm algo) {
        Node srcNode = getNodeByLabel(src);
        Node dstNode = getNodeByLabel(dst);

        return GraphSearch(srcNode, dstNode, algo);
    }

    public Path GraphSearch(Node src, Node dst, Algorithm algo) {
        SearchStrategy strategy;

        if (algo == Algorithm.BFS) {
            strategy = new BFSSearch();
        } else if (algo == Algorithm.DFS) {
            strategy = new DFSSearch();
        } else if (algo == Algorithm.RANDOM_WALK) {
            strategy = new RandomWalkSearch();
        } else {
            return null;
        }

        return strategy.search(src, dst, this);
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