package org.at.cig.util;

import org.at.cig.common.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TreeVisitor {

    private Node visitedNode;

    private Map<Node, Boolean> nullability = new HashMap<Node, Boolean>();
    private Map<Node, Set<Integer>> firstPos = new HashMap<Node, Set<Integer>>();
    private Map<Node, Set<Integer>> lastPos = new HashMap<Node, Set<Integer>>();
    private Map<Integer, Set<Integer>> followPos = new HashMap<Integer, Set<Integer>>();

    private int position = 1;

    public void run(Node node) {
        visit(node);
        System.out.println("--------- FOLLOWPOS---------");
        for(Integer position : followPos.keySet()) {
            System.out.println("Position " + position + " ==> " + followPos.get(position));
        }
    }

    public void visit(Node visitedNode) {
        for (Node node : visitedNode.getTransitions().values()) {
            visit(node);
        }
        handleNode(visitedNode);
        print();
    }

    private void handleNode(Node node) {
        visitedNode = node;
        computeNullability(node);
        computeFirstPos(node);
        computeLastPos(node);
        computeFollowPos(node);
    }


    private Node getFirstChild() {
        return (Node) visitedNode.getTransitions().values().toArray()[0];
    }

    private Node getSecondChild() {
        return (Node) visitedNode.getTransitions().values().toArray()[1];
    }

    public void computeFollowPos(Node node) {
        if (isNodeCat()) {
            for(Integer position : lastPos.get(getFirstChild())) {
                if(!followPos.containsKey(position)) {
                    followPos.put(position, new HashSet<Integer>());
                }
                followPos.get(position).addAll(firstPos.get(getSecondChild()));
            }
        }
        if(isNodeStar()) {
            for(Integer position : lastPos.get(visitedNode)) {
                if(!followPos.containsKey(position)) {
                    followPos.put(position, new HashSet<Integer>());
                }
                followPos.get(position).addAll(firstPos.get(visitedNode));
            }
        }
    }

    public void computeFirstPos(Node node) {
        if (isNodeOperand()) {
            firstPos.put(node, new HashSet<Integer>());
            firstPos.get(node).add(position);
            position++;
        }
        if (isNodeOr()) {
            firstPos.put(node, new HashSet<Integer>());
            firstPos.get(node).addAll(firstPos.get(getFirstChild()));
            firstPos.get(node).addAll(firstPos.get(getSecondChild()));
        }
        if (isNodeCat()) {
            if (isNullable(getFirstChild())) {
                firstPos.put(node, new HashSet<Integer>());
                firstPos.get(node).addAll(firstPos.get(getFirstChild()));
                firstPos.get(node).addAll(firstPos.get(getSecondChild()));
            } else {
                firstPos.put(node, new HashSet<Integer>());
                firstPos.get(node).addAll(firstPos.get(getFirstChild()));
            }
        }
        if(isNodeStar()) {
            firstPos.put(node, new HashSet<Integer>());
            firstPos.get(node).addAll(firstPos.get(getFirstChild()));
        }
    }

    public void computeLastPos(Node node) {
        if (isNodeOperand()) {
            lastPos.put(node, new HashSet<Integer>());
            lastPos.get(node).addAll(firstPos.get(node));
        }
        if (isNodeOr()) {
            lastPos.put(node, new HashSet<Integer>());
            lastPos.get(node).addAll(lastPos.get(getFirstChild()));
            lastPos.get(node).addAll(lastPos.get(getSecondChild()));
        }
        if (isNodeCat()) {
            if (isNullable(getSecondChild())) {
                lastPos.put(node, new HashSet<Integer>());
                lastPos.get(node).addAll(lastPos.get(getFirstChild()));
                lastPos.get(node).addAll(lastPos.get(getSecondChild()));
            } else {
                lastPos.put(node, new HashSet<Integer>());
                lastPos.get(node).addAll(lastPos.get(getSecondChild()));
            }
        }
        if(isNodeStar()) {
            lastPos.put(node, new HashSet<Integer>());
            lastPos.get(node).addAll(lastPos.get(getFirstChild()));
        }
    }

    public void computeNullability(Node node) {
        if (isNodeOperand()) {
            setNodeNonNullable();
        }
        if (isNodeStar()) {
            setNodeNullable();
        }
        if (isNodeOr()) {
            if (isNullable(getFirstChild()) || isNullable(getSecondChild())) {
                setNodeNullable();
            } else {
                setNodeNonNullable();
            }
        }
        if (isNodeCat()) {
            if (isNullable(getFirstChild()) && isNullable(getSecondChild())) {
                setNodeNullable();
            } else {
                setNodeNonNullable();
            }
        }

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

    private void setNodeNonNullable() {
        nullability.put(visitedNode, false);
    }

    private void setNodeNullable() {
        nullability.put(visitedNode, true);
    }

    private boolean isNullable(Node node) {
        return nullability.get(node);
    }

    private void print() {
        System.out.println(visitedNode.getName() + (isNullable(visitedNode) ? "   NULLABLE " : "   NOT NULLABLE "));
        System.out.println("First Pos Is : " + firstPos.get(visitedNode));
        System.out.println("Last Pos Is : " + lastPos.get(visitedNode));
        System.out.println("--------------------------------");
    }
}
