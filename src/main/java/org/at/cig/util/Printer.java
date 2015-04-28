package org.at.cig.util;


import org.at.cig.common.Node;
import org.at.cig.common.Transition;

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
