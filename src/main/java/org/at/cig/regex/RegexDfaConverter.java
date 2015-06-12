package org.at.cig.regex;

import org.at.cig.common.*;
import org.at.cig.util.InfixPostfixConverter;
import org.at.cig.util.RegexParser;
import org.at.cig.util.TreeVisitor;

import java.util.*;

/**
 * Created by otarasenko on 5/4/15.
 */
public class RegexDfaConverter {

    private Set<String> alphabet = new HashSet<String>();
    private Map<Integer, Node> operandPosition;
    private Map<Integer, Set<Integer>> followPos;
    private Map<Node, Set<Integer>> firstPos;
    private StateHolder dStates = new StateHolder();
    private TransitionTable dTran = new TransitionTableImpl();

    public TransitionTable convert() {
        initDStates();
        while (dStates.hasUnmarked()) {
            State s = dStates.next();
            for (String a : alphabet) {
                State u = new State();
                for (Object position : s) {
                    if (operandPosition.get(position).getName().equals(a)) {
                        //u.merge(followPos.get(operandPosition.get(position)));
                        u.merge(followPos.get(position));
                    }
                }
                if (!dStates.contains(u)) {
                    dStates.add(u);
                }
                dTran.addTransition(s, a, u);
            }
        }
        return dTran;
    }


    private void initDStates() {
        for (Node node : firstPos.keySet()) {
            if (node.isStart()) {
                dStates.add(firstPos.get(node));

            }
        }
    }

    public TransitionTable run() {
        /*       TreeBuilder builder = new TreeBuilder();
Node treeRoot =
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
        .build();*/
        InfixPostfixConverter infixPostfixConverter = new InfixPostfixConverter();
        String converted = infixPostfixConverter.handle("(a|b)*abb#");
        RegexParser parser = new RegexParser();
        TreeVisitor visitor = new TreeVisitor();
        visitor.run(parser.parse(converted));
        this.operandPosition = visitor.getOperandPosition();
        this.firstPos = visitor.getFirstPos();
        this.followPos = visitor.getFollowPos();
        this.alphabet = visitor.getAlphabet();
        //noinspection UnnecessaryLocalVariable
        TransitionTable transitionTable = convert();
      //  Printer.outDotTransitionTable(transitionTable);
        return transitionTable;

     //   dTran.print();
    }

    public static void main(String[] args) {
        RegexDfaConverter converter = new RegexDfaConverter();
        converter.run();

    }

}
