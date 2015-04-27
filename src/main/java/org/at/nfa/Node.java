package org.at.nfa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/20/15
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    public char value;
    public String type;
    public Node parent;
    public List<Node> children = new ArrayList<Node>();

    public Node(char symbol) {
        value = symbol;
    }

    public Node() {

    }

    public void addChild(Node nodeSymbol) {
        children.add(nodeSymbol);
        nodeSymbol.parent = this;
    }
}
