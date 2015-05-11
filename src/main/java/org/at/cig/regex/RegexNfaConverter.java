package org.at.cig.regex;

import org.at.cig.common.Fragment;
import org.at.cig.common.Node;
import org.at.cig.util.InfixPostfixConverter;
import org.at.cig.util.Printer;

import java.util.Stack;

// This class converts Reg to  NFA using algorithm 3.23 (McNaughton-Yamada- Thompson)
public class RegexNfaConverter {


    public Fragment concat(Fragment s, Fragment t) {
        copyStartToTail(t.getStartNode(), s.getTailNode());
        s.setTailNode(t.getTailNode());
        return s;
    }

    private void copyStartToTail(Node start , Node tail) {
        tail.getTransitions().putAll(start.getTransitions());
        start.getTransitions().clear();
        Node.getNodes().remove(start);
        tail.setFinish(false);
    }



    public Fragment or(Fragment sF, Fragment tF) {
        makeNodeInternal(sF.getStartNode());
        makeNodeInternal(tF.getStartNode());
        makeNodeInternal(sF.getTailNode());
        makeNodeInternal(tF.getTailNode());

        Fragment fragment = new Fragment();
        fragment.addEmptyTransitionFromStartNodeToNode(sF.getStartNode());
        fragment.addEmptyTransitionFromStartNodeToNode(tF.getStartNode());

        sF.addEmptyTransitionFromTailNodeToNode(fragment.getTailNode());
        tF.addEmptyTransitionFromTailNodeToNode(fragment.getTailNode());
        return fragment;
    }



    public Fragment star(Fragment sF) {
        Fragment fragment = new Fragment();
        fragment.addEmptyTransitionFromStartNodeToNode(sF.getStartNode());
        makeNodeInternal(sF.getStartNode());
        fragment.addEmptyTransitionFromStartNodeToNode(fragment.getTailNode());
        makeNodeInternal(sF.getTailNode());
        sF.addEmptyTransitionFromTailNodeToNode(fragment.getTailNode());
        sF.addEmptyTransitionFromTailNodeToNode(sF.getStartNode());
        return fragment;
    }


    public Fragment operand(String s) {
        Fragment fragment = new Fragment();
        fragment.addTransitionFromStartNodeToNode(s, fragment.getTailNode());
        return fragment;
    }


    public void run() {
        InfixPostfixConverter infixPostfixConverter = new InfixPostfixConverter();
        //String converted = infixPostfixConverter.handle("(a|b)*abb");
        // String converted = infixPostfixConverter.handle("baa|a");
        String converted = infixPostfixConverter.handle("abb(a|bc*|ba)");
        convert(converted);
        Printer.outDot(Node.getNodes());
    }

    public static void main(String[] args) {
        RegexNfaConverter converter = new RegexNfaConverter();
        converter.run();
    }

    private Node makeNodeInternal(Node node) {
        node.setFinish(false);
        node.setStart(false);
        return node;
    }

    public Fragment convert(String s) {
        Stack<Fragment> stack = new Stack<Fragment>();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                default:
                    stack.push(operand(Character.toString(s.charAt(i))));
                    break;
                case '*':
                    Fragment node = stack.pop();
                    node = star(node);
                    stack.push(node);
                    break;
                case '.':
                    Fragment fragmentT = stack.pop();
                    Fragment fragmentS = stack.pop();
                    Fragment fragmentST = concat(fragmentS, fragmentT);
                    stack.push(fragmentST);
                    break;
                case '|':
                    fragmentT = stack.pop();
                    fragmentS = stack.pop();
                    Fragment fragmentSorT = or(fragmentS, fragmentT);
                    stack.push(fragmentSorT);
                    break;
            }
        }
        return stack.pop();
    }


}
