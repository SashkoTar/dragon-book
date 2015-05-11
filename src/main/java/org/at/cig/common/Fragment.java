package org.at.cig.common;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 5/10/15
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class Fragment {

    private Node startNode;

    private Node tailNode;

    public Fragment() {
        this(Node.build(), Node.build());
    }

    public Fragment(Node start, Node tail) {
        this.startNode = start;
        this.tailNode = tail;
        this.startNode.setStart(true);
        this.startNode.setFinish(false);
        this.tailNode.setStart(false);
        this.tailNode.setFinish(true);
    }

    public void setTailNode(Node tailNode) {
        this.tailNode = tailNode;
    }

    public Node getStartNode() {

        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getTailNode() {
        return tailNode;
    }

    public void addEmptyTransitionFromStartNodeToNode(Node target) {
        startNode.addEmptyTransitionToNode(target);
    }

    public void addTransitionFromStartNodeToNode(String s, Node target) {
        startNode.addTransitionToNode(s, target);
    }

    public void addEmptyTransitionFromTailNodeToNode(Node target) {
        tailNode.addEmptyTransitionToNode(target);
    }
}
