package org.at.cig.util;


import org.at.cig.common.Node;
import org.at.cig.common.Transition;

import java.util.Set;

public class Printer {

    public static void out(Set<Node> nodes) {
        for(Node node : nodes) {
            String type = "INTERNAL";
            if(node.isStart()) {
                type = "START";
            }
            if(node.isFinish()) {
                type = "FINISH";
            }
            if(node.isFinish() && node.isStart()) {
                type = "BOTH";
            }

            System.out.println(node.getName() + "  TYPE = " + type);
            for(Transition transition : node.getTransitions().keySet()) {
                if(transition.isEmty()) {
                    System.out.println("\tEpsilon --> " + node.getTransitions().get(transition).getName());
                }else {
                    System.out.println("\t"+transition.getSymbol() + " --> " + node.getTransitions().get(transition).getName());
                }
            }
        }
    }

    // http://graphs.grevian.org/graph/6058848288768000
    public static void outDot(Set<Node> nodes) {
        for(Node node : nodes) {
            for(Transition transition : node.getTransitions().keySet()) {
                if(transition.isEmty()) {
                    System.out.println(node.getName() + " -> " + node.getTransitions().get(transition).getName() + ";");
                }else {
                    System.out.println(node.getName() + " -> " + node.getTransitions().get(transition).getName() + " [label=\"" + transition.getSymbol() + "\"];");



                }
            }
        }
    }

}
