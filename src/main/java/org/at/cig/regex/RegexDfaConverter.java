package org.at.cig.regex;

import org.at.cig.common.Node;
import org.at.cig.common.State;
import org.at.cig.common.StateHolder;
import org.at.cig.common.TransitionTable;
import org.at.cig.dsl.TreeBuilder;
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
    private TransitionTable dTran = new TransitionTable();

    public void convert() {
        initDStates();
        while(dStates.hasUnmarked()) {
            State s = dStates.next();
            for(String a : alphabet) {
                State u = new State();
                 for(Integer position : s) {
                    if(operandPosition.get(position).getName().equals(a)){
                        //u.merge(followPos.get(operandPosition.get(position)));
                        u.merge(followPos.get(position));
                    }
                 }
                if(!dStates.contains(u)) {
                    dStates.add(u);
                }
                dTran.addTransition(s, a, u);
            }
        }
    }


    private void initDStates() {
        for(Node node : firstPos.keySet()) {
         if(node.isStart()) {
             dStates.add(firstPos.get(node));

         }
        }
    }

    public void run() {
        TreeBuilder builder = new TreeBuilder();
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
                        .build();

       TreeVisitor visitor =  new TreeVisitor();
        visitor.run(treeRoot);
        this.operandPosition = visitor.getOperandPosition();
        this.firstPos = visitor.getFirstPos();
        this.followPos = visitor.getFollowPos();
        alphabet.add("a");
        alphabet.add("b");
        convert();
        dTran.print();
    }

    public static void main(String [] args) {
        RegexDfaConverter converter = new RegexDfaConverter();
        converter.run();
    }

}
