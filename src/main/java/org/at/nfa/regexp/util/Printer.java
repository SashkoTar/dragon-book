package org.at.nfa.regexp.util;


import org.at.nfa.regexp.Node;
import org.at.nfa.regexp.Transition;

import java.util.Set;

public class Printer {

    public static void out(Set<Node> nodes) {
        for(Node node : nodes) {
            System.out.println(node.getName());
            for(Transition transition : node.getTransitions().keySet()) {
                if(transition.isEmty()) {
                    System.out.println("\tEpsilon --> " + node.getTransitions().get(transition).getName());
                }else {
                    System.out.println("\t"+transition.getSymbol() + " --> " + node.getTransitions().get(transition).getName());
                }
            }
        }
    }

}
