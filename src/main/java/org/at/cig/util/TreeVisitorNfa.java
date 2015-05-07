package org.at.cig.util;

import org.at.cig.common.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TreeVisitorNfa {

    private Node visitedNode;



    private int position = 1;

    public void run(Node node) {
        visit(node);

    }

    public void visit(Node visitedNode) {
        handleChildren(visitedNode);
        handleNode(visitedNode);
        print();
    }

    private void handleChildren(Node visitedNode) {
        for (Node node : visitedNode.getTransitions().values()) {
            visit(node);
        }
    }


    private void handleNode(Node node) {
        visitedNode = node;
    }


    private Node getFirstChild() {
        return (Node) visitedNode.getTransitions().values().toArray()[0];
    }

    private Node getSecondChild() {
        return (Node) visitedNode.getTransitions().values().toArray()[1];
    }





    private boolean isNodeOperand() {
        return visitedNode.getType() == Node.NodeType.OPERAND;
    }

    private boolean isNodeOr() {
        return visitedNode.getType() == Node.NodeType.OR;
    }

    private boolean isNodeCat() {
        return visitedNode.getType() == Node.NodeType.CAT;
    }

    private boolean isNodeStar() {
        return visitedNode.getType() == Node.NodeType.STAR;
    }


    private void print() {
        //System.out.println(visitedNode.getName() + visitedNode ? "   NULLABLE " : "   NOT NULLABLE "));
        System.out.println("First Pos Is : " + visitedNode);
        System.out.println("Last Pos Is : " + visitedNode);
        System.out.println("--------------------------------");
    }


}
