package org.at.cig.dsl;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;

import java.util.Set;

/**
 * Created by otarasenko on 4/29/15.
 */
public class GraphBuilder {

    private Node nodeStart;
    private Node nodeTo;
    private Transition transition;
    private Node currentNode;

    public GraphBuilder() {
        Node.getNodes().clear();
    }

    public GraphBuilder from(String nodeName) {
        if(nodeStart == null) {
            nodeStart = Node.build(nodeName);
        }
        currentNode = findOrCreateNodeByName(nodeName);
        return this;
    }

    public GraphBuilder on(String symbol) {
        transition = new Transition(symbol);
        return this;
    }

    public GraphBuilder onEmpty() {
        transition = new Transition();
        return this;
    }

    public GraphBuilder goTo(String nodeName) {
        nodeTo = findOrCreateNodeByName(nodeName);
        currentNode.getTransitions().put(transition, nodeTo);
        return this;
    }

    public GraphBuilder goToFinal(String nodeName) {
        nodeTo = findOrCreateNodeByName(nodeName);
        nodeTo.setFinish(true);
        currentNode.getTransitions().put(transition, nodeTo);
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



}
