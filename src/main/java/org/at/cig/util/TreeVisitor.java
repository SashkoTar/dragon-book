package org.at.cig.util;

import org.at.cig.common.Node;

/**
 * Created by otarasenko on 4/30/15.
 */
public class TreeVisitor {

    public void visit(Node visitedNode) {
        for (Node node : visitedNode.getTransitions().values()) {
            visit(node);
        }
        handleNode(visitedNode);
    }

    private void handleNode(Node node) {
        System.out.println(node.getName());
    }
}
