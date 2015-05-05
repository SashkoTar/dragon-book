package org.at.cig.util;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;

import java.util.Stack;


public class RegexParser {

    public Node parse(String regex) {
        Stack<Node> stack = new Stack<Node>();
        for(char symbol : regex.toCharArray()) {
            switch (symbol){
                case '|' :
                    Node node = Node.build(getNodeName());
                    node.setType(Node.NodeType.OR);
                    Node rightChild = stack.pop();
                    Node leftChild = stack.pop();
                    node.getTransitions().put(new Transition(), leftChild);
                    node.getTransitions().put(new Transition(), rightChild);
                    stack.push(node);
                    break;
                case '.' :
                    node = Node.build(getNodeName());
                    node.setType(Node.NodeType.CAT);
                    rightChild = stack.pop();
                    leftChild = stack.pop();
                    node.getTransitions().put(new Transition(), leftChild);
                    node.getTransitions().put(new Transition(), rightChild);
                    stack.push(node);
                    break;
                case '*' :
                    node = Node.build(getNodeName());
                    node.setType(Node.NodeType.STAR);
                    node.getTransitions().put(new Transition(), stack.pop());
                    stack.push(node);
                    break;
                default:
                    node = Node.build(Character.toString(symbol));
                    node.setType(Node.NodeType.OPERAND);
                    stack.push(node);
                    break;
            }
        }
        Node root = stack.pop();
        root.setStart(true);
        return root;
    }

    int i = -1;

    private String getNodeName() {
        i++;
        return "s"+i;
    }

    private void print() {
        Printer.out(Node.getNodes());
    }
}
