package org.at.cig.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Node {
    private boolean start;
    private boolean finish;
    private Map<Transition, Node> transitions;
    private static Set<Node> nodes = new HashSet<Node>();
    private String name;
    private static int count = 0;

    private Node() {
        transitions = new HashMap<Transition, Node>();
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Map<Transition, Node> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<Transition, Node> transitions) {
        this.transitions = transitions;
    }

    public static Node build() {
        return build("States-"+count);
    }

    public static Node build(String name) {
        Node node = new Node();
        node.name = name;
        count++;
        nodes.add(node);
        return node;
    }

    public String getName() {
        return name;
    }

    public static Set<Node> getNodes() {
        return nodes;
    }
}
