package org.at.cig.dsl;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;

import java.util.Set;


public class TreeBuilder {

    Node nodeStart;
    Node nodeTo;
    Transition transition;
    Node currentNode;

    public TreeBuilder() {
        Node.getNodes().clear();
    }

    public TreeBuilder from(String nodeName) {
        if(nodeStart == null) {
            nodeStart = Node.build(nodeName);
        }
        currentNode = findOrCreateNodeByName(nodeName);
        return this;
    }

    public TreeBuilder fromRoot(String nodeName) {
        from(nodeName);
        nodeStart.setType(Node.NodeType.CAT);
        nodeStart.setStart(true);
        return from(nodeName);
    }



    public TreeBuilder onEmpty() {
        transition = new Transition();
        return this;
    }



    public Node build() {
        return nodeStart;
    }

    private Node findOrCreateNodeByName(String nodeName) {
        Set<Node> nodes = Node.getNodes();
        for(Node node : nodes) {
            if(node.getName().equals(nodeName)) {
                return node;
            }
        }
        return Node.build(nodeName);
    }


    public TreeBuilder addCatChild(String nodeName) {
        nodeTo = findOrCreateNodeByName(nodeName);
        nodeTo.setType(Node.NodeType.CAT);
        onEmpty();
        currentNode.getTransitions().put(transition, nodeTo);
        return this;
    }

    public TreeBuilder addLeafChild(String nodeName) {
        nodeTo = Node.build(nodeName);
        nodeTo.setType(Node.NodeType.OPERAND);
        onEmpty();
        currentNode.getTransitions().put(transition, nodeTo);
        return this;
    }

    public TreeBuilder addStarChild(String nodeName) {
        nodeTo = findOrCreateNodeByName(nodeName);
        nodeTo.setType(Node.NodeType.STAR);
        onEmpty();
        currentNode.getTransitions().put(transition, nodeTo);
        return this;
    }

    public TreeBuilder addOrChild(String nodeName) {
        nodeTo = findOrCreateNodeByName(nodeName);
        nodeTo.setType(Node.NodeType.OR);
        onEmpty();
        currentNode.getTransitions().put(transition, nodeTo);
        return this;
    }
}
