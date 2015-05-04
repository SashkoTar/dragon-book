package org.at.cig.util;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.at.cig.dsl.TreeBuilder;
import org.junit.Test;

import java.util.Stack;


public class TreeBuilderTest {

    // @Test
    public void shouldBuildAndPrintGraph() {
        TreeBuilder builder = new TreeBuilder();
        builder
                .fromRoot("s0")
                .addCatChild("s1")
                .addLeafChild("#")
                .from("s1")
                .addCatChild("s2")
                .addLeafChild("b")
                .from("s2")
                .addCatChild("s3")
                .addLeafChild("b")
                .from("s3")
                .addStarChild("s4")
                .addLeafChild("a")
                .from("s4")
                .addOrChild("s5")
                .from("s5")
                .addLeafChild("a")
                .addLeafChild("b")
                .build();

        print();
    }

    //   @Test
    public void shouldBuildAndPrintGraph2() {
        TreeBuilder builder = new TreeBuilder();
        builder
                .fromRoot("s0")
                .addCatChild("s1")
                .addLeafChild("#")
                .from("s1")
                .addLeafChild("a")
                .addLeafChild("b")

                .build();

        print();
    }

    @Test
    public void shouldBuildFromPostfixString() {
        Stack<Node> stack = new Stack<Node>();
        TreeBuilder builder = new TreeBuilder();
        StringBuilder input = new StringBuilder("ab|*a.b.b.#.");
        String[] tokens = input.reverse().toString().split("");
        for(String symbol : tokens) {
            if(isOperand(symbol)) {
                stack.push(addLeafNode(symbol));
            }
            if(isOr(symbol)) {
                Node or = Node.build();
                Node rightChild = stack.pop();
                Node leftChild = stack.pop();
                or.getTransitions().put(new Transition(), leftChild);
                or.getTransitions().put(new Transition(), rightChild);
                stack.push(or);
            }
        }

    }

    private boolean isOr(String symbol) {
        return false;
    }

    private Node addLeafNode(String symbol) {
        return Node.build();
    }

    private boolean isOperand(String symbol) {
        return true;
    }

    private void print() {
        Printer.out(Node.getNodes());
    }
}
