package org.example;

import java.util.*;

public class Graph {
    // Feature 2 commit
    // Feature 4: export graph to DOT and PNG using Graphviz
    // store nodes
    private Set<String> nodes;

    // store edges
    private List<String[]> edges;

    // constructor
    public Graph() {
        nodes = new HashSet<>();
        edges = new ArrayList<>();
    }

    // Feature 2: add a single node
    public void addNode(String label) {

        if (!nodes.contains(label)) {
            nodes.add(label);
        }

    }

    // Feature 2: add multiple nodes
    public void addNodes(String[] label) {

        for (String s : label) {
            addNode(s);
        }

    }
    // Feature 3: add edges to the graph
    public void addEdge(String srcLabel, String dstLabel) {

        // add nodes automatically if they don't exist
        addNode(srcLabel);
        addNode(dstLabel);

        // check if edge already exists
        for (String[] edge : edges) {
            if (edge[0].equals(srcLabel) && edge[1].equals(dstLabel)) {
                return; // duplicate edge
            }
        }

        // add new edge
        edges.add(new String[]{srcLabel, dstLabel});
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

            // create DOT file first
            String dotFile = path + ".dot";
            outputDOTGraph(dotFile);

            // output image file
            String outputFile = path + "." + format;

            // run Graphviz command
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