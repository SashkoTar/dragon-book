package org.at.cig.regex;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.at.cig.util.InfixPostfixConverter;
import org.at.cig.util.Printer;
import org.at.cig.util.RegexParser;
import org.at.cig.util.TreeVisitor;

import java.util.Stack;

// This class converts Reg to  NFA using algorithm 3.23 (McNaughton-Yamada- Thompson)
public class RegexNfaConverter {

    public Node concat(Node s, Node t) {
        for(Transition transition : s.getTransitions().keySet()) {
            if(s.getTransitions().get(transition).isFinish()) {
                t.setStart(false);
                s.getTransitions().put(transition, t);
            }
        }
        return s;
    }

    public Node or(Node sStart, Node tStart) {
        Node finish = Node.build();
        finish.setFinish(true);

        sStart.setStart(false);
        sStart.setFinish(false);

        Node s = getFirstTransition(sStart);
        s.setFinish(false);
        s.getTransitions().put(new Transition(), finish);

        tStart.setStart(false);
        tStart.setFinish(false);

        Node t = getFirstTransition(tStart);
        t.setFinish(false);

        t.getTransitions().put(new Transition(), finish);

        Node start = Node.build();
        start.setStart(true);
        start.getTransitions().put(new Transition(), sStart);
        start.getTransitions().put(new Transition(), tStart);

        return start;
    }

    public Node star(Node node) {
        node.setStart(false);

        Node start = Node.build();
        start.setStart(true);
        Node finish = Node.build();
        finish.setFinish(true);
        start.getTransitions().put(new Transition(), finish);
        start.getTransitions().put(new Transition(), node);

        //Replace Finish Node
        for(Transition transition : node.getTransitions().keySet()) {
            if(node.getTransitions().get(transition).isFinish()) {
              //  s.getTransitions().put(transition, t);
                node.getTransitions().get(transition).setFinish(false);
                node.getTransitions().get(transition).getTransitions().put(new Transition(), finish);
                node.getTransitions().get(transition).getTransitions().put(new Transition(), node);
            }
        }

        return start;
    }

    public Node operand(String s) {
        Node startNode = Node.build();
        startNode.setStart(true);
        Transition a = new Transition(s);
        Node finishNode = Node.build();
        finishNode.setFinish(true);

        startNode.getTransitions().put(a, finishNode);
        return startNode;
    }

    private Node getFirstTransition(Node node) {
        for(Transition transition : node.getTransitions().keySet()) {
            return node.getTransitions().get(transition);
        }
        return null;
    }


    public void run() {
        InfixPostfixConverter infixPostfixConverter = new InfixPostfixConverter();
         //String converted = infixPostfixConverter.handle("(a|b)*abb");
         String converted = infixPostfixConverter.handle("baa|a");
        convert(converted);
        Printer.outDot(Node.getNodes());
    }

    public static void main(String[] args) {
        RegexNfaConverter converter = new RegexNfaConverter();
        converter.run();
    }

    public Node convert(String s) {
        Stack<Node> stack = new Stack<Node>();
        for(int i=0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                default:
                    stack.push(operand(Character.toString(s.charAt(i))));
                    break;
                case '*':
                    Node node = stack.pop();
                    node = star(node);
                    stack.push(node);
                    break;
                case '.':
                    Node tNode = stack.pop();
                    Node sNode = stack.pop();
                    Node cNode = concat(sNode, tNode);
                    stack.push(cNode);
                    break;
                case '|':
                    tNode = stack.pop();
                    sNode = stack.pop();
                    cNode = or(sNode, tNode);
                    stack.push(cNode);
                    break;
            }
        }
        return stack.pop();
    }
}
