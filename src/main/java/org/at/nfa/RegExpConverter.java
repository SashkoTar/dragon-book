package org.at.nfa;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/20/15
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegExpConverter {


    public Node convert(Node leftChild, String regExp) {
        Node parent = new Node();
        String nextSuffix;
        if (regExp.equals("")) {
            return leftChild;
        }
        if (leftChild != null) {
            parent.addChild(leftChild);
        }
        switch (regExp.charAt(0)) {
            case '|':
                parent.addChild(new Node('|'));
                parent.addChild(getState(regExp.charAt(1)));
                nextSuffix = regExp.substring(2);
                break;
            case '*':
                parent.addChild(new Node('*'));
                nextSuffix = regExp.substring(1);
                break;
            default:
                if (leftChild == null) {
                    parent = getState(regExp.charAt(0));
                } else{
                    parent.addChild(getState(regExp.charAt(0)));
                }
                    nextSuffix = regExp.substring(1);
                break;
        }
        return convert(parent, nextSuffix);  //To change body of created methods use File | Settings | File Templates.
    }

    private Node getState(char c) {
        Node nodeSymbol = new Node(c);
        Node stateNode = new Node();
        stateNode.addChild(nodeSymbol);
        return stateNode;
    }
}
