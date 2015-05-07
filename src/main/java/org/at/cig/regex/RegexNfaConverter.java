package org.at.cig.regex;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.at.cig.util.InfixPostfixConverter;
import org.at.cig.util.Printer;
import org.at.cig.util.RegexParser;
import org.at.cig.util.TreeVisitor;

// This class converts Reg to  NFA using algorithm 3.23 (McNaughton-Yamada- Thompson)
public class RegexNfaConverter {

    public Node concat(Node s, Node t) {
        for(Transition transition : s.getTransitions().keySet()) {
            if(s.getTransitions().get(transition).isFinish()) {
                s.getTransitions().put(transition, t);
            }
        }
        return s;
    }

    public Node or(Node sStart, Node tStart) {
        Node finish = Node.build();
        finish.setFinish(true);

        Node s = getFirstTransition(sStart);
        s.getTransitions().put(new Transition(), finish);

        Node t = getFirstTransition(tStart);
        t.getTransitions().put(new Transition(), finish);

        Node start = Node.build();
        start.getTransitions().put(new Transition(), s);
        start.getTransitions().put(new Transition(), t);

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

    private Node getFirstTransition(Node node) {
        for(Transition transition : node.getTransitions().keySet()) {
            return node.getTransitions().get(transition);
        }
        return null;
    }

    public void run() {

        InfixPostfixConverter infixPostfixConverter = new InfixPostfixConverter();
        String converted = infixPostfixConverter.handle("(a|b)*abb");
        RegexParser parser = new RegexParser();
        Node node = parser.parse(converted);
        Printer.out(Node.getNodes());
   //     TreeVisitor visitor = new TreeVisitor();
    //    visitor.run(parser.parse(converted));

    }

    public static void main(String[] args) {
        RegexNfaConverter converter = new RegexNfaConverter();
        converter.run();
    }

}
