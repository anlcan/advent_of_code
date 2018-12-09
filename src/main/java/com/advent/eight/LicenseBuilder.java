package com.advent.eight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LicenseBuilder {

    private final List<Integer> input;
    private Node rootNode;

    public LicenseBuilder(final String spacedInput) {
        this.input = Arrays.stream(spacedInput.split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        rootNode = parseNodes(input, 1).get(0);
    }

    public int compute() {
        return rootNode.compute();
    }

    private static List<Node> parseNodes(final List<Integer> input, int numberOfNodes) {

        assert (numberOfNodes >= 0);


        List<Node> result = new ArrayList<>();
        while (numberOfNodes-- > 0) {

            System.out.println("input: " + input);
            int skip = result.stream().mapToInt(Node::len).sum();
            int childNodes = input.get(skip);
            int metaData = input.get(skip+1);

            Node node = new Node(childNodes, metaData);
            System.out.println("Parsing:" + node);

            List<Integer> input1 = new ArrayList<>();
            input1.addAll(input.subList(skip+2, input.size()));
            List<Node> nodeList = parseNodes(input1, node.numberOfChildNodes);
            System.out.println("..found subnodes: " + nodeList.size());
            node.childNodes.addAll(nodeList);

            input.stream()
                    .skip(skip+2) // childnodes and metadata
                    .skip(node.childNodes.stream().mapToInt(Node::len).sum()) // childnodes content
                    .limit(node.numberOfMetadataEntries)
                    .forEach(node.metaData::add);


            result.add(node);
        }

        return result;

    }

}
