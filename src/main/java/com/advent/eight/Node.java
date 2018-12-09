package com.advent.eight;

import java.util.*;

public class Node {

    public final String name;
    public final int numberOfChildNodes;
    public final int numberOfMetadataEntries;

    public final List<Node> childNodes = new ArrayList<>();
    public final List<Integer> metaData = new ArrayList<>();

    public static char nodeName = 'A';

    public Node(int numberOfChildNodes, int numberOfMetaDataEntries) {
        name = String.valueOf(nodeName++);
        this.numberOfChildNodes = numberOfChildNodes;
        this.numberOfMetadataEntries = numberOfMetaDataEntries;
    }

    public static Node build(List<Node> nodes, List<Integer> metaData) {
        Node node = new Node(nodes.size(), metaData.size());

        node.childNodes.addAll(nodes);
        node.metaData.addAll(metaData);

        return node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return numberOfChildNodes == node.numberOfChildNodes &&
                numberOfMetadataEntries == node.numberOfMetadataEntries &&
                Objects.equals(childNodes, node.childNodes) &&
                Objects.equals(metaData, node.metaData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfChildNodes, numberOfMetadataEntries, childNodes, metaData);
    }

    public int sum() {
        assert (numberOfMetadataEntries == metaData.size());
        return metaData.stream().reduce(0, Integer::sum);
    }

    // If a node has no child nodes, its value is the sum of its metadata entries.
    // However, if a node does have child nodes, the metadata entries become
    // indexes which refer to those child nodes.
    // A metadata entry of 1 refers to the first child node,
    // 2 to the second, 3 to the third, and so on.
    //
    // The value of this node is the sum of the values of the child nodes
    // referenced by the metadata entries. If a referenced child node does not exist,
    // that reference is skipped.
    // A child node can be referenced multiple time and counts each time it is referenced.
    // A metadata entry of 0 does not refer to any child node.
    public int compute() {
        assert (numberOfChildNodes == childNodes.size());
        if (childNodes.size() == 0) {
            return sum();
        } else {

            return metaData
                    .stream()
                    .mapToInt(this::evaulateMetadata)
                    .sum();

        }
    }

    private int evaulateMetadata(final Integer metada) {
        if (metada == 0) {
            return 0;
        } else if (metada - 1 < childNodes.size()) {
            return childNodes.get(metada - 1).compute();
        } else
            return 0;
    }

    public int computeEX() {
        assert (numberOfChildNodes == childNodes.size());
        return sum() + childNodes.stream().mapToInt(Node::compute).sum();

    }


    public int len() {
        return 2 + numberOfMetadataEntries + childNodes.stream().mapToInt(Node::len).sum();
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", numberOfChildNodes=" + numberOfChildNodes +
                ", numberOfMetadataEntries=" + numberOfMetadataEntries +
                '}';
    }
}
