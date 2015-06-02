package org.at.cig.util;


import org.at.cig.common.Node;
import org.at.cig.common.State;
import org.at.cig.common.Transition;
import org.at.cig.common.TransitionTableImpl;

import java.util.HashMap;
import java.util.Map;
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
        String finishNodes = "node [shape = doublecircle]; ";
        String startNodes = "node [shape = Mcircle]; ";
        for(Node node: nodes) {
           if(node.isFinish()) {
               finishNodes += " " + node.getName();
           }
            if(node.isStart()) {
                startNodes += " " + node.getName();
            }
        }
        System.out.println("rankdir=TB;");
        System.out.println("size=\"8,5\"");
        System.out.println(startNodes + ";");
        System.out.println(finishNodes + ";");
        System.out.println("node [shape = circle];");
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

    public static void outDotTransitionTable(TransitionTableImpl transitionTable) {

        Map<State, Map<String, State>> dTransition = transitionTable.getdTransition();


        System.out.println("rankdir=TB;");
        System.out.println("size=\"8,5\"");
//        System.out.println(startNodes + ";");
//        System.out.println(finishNodes + ";");
        System.out.println("node [shape = circle];");
        for(Set<Integer> state : dTransition.keySet()) {
            for (String i : dTransition.get(state).keySet()) {
                if(dTransition.get(state).get(i).size() == 0) {
                    continue;
                }
                System.out.println(convertStateToString(state) + " -> " + convertStateToString(dTransition.get(state).get(i)) + " [label=\"" + i + "\"];");
            }
        }
    }

    public static void outDot3(Map<Set<Integer>, Map<Integer, Set<Integer>>> transitionTable) {
        String finishNodes = "node [shape = doublecircle]; ";
        String startNodes = "node [shape = Mcircle]; ";

        System.out.println("rankdir=TB;");
        System.out.println("size=\"8,5\"");
//        System.out.println(startNodes + ";");
//        System.out.println(finishNodes + ";");
        System.out.println("node [shape = circle];");
        for(Set<Integer> state : transitionTable.keySet()) {
            for (Integer i : transitionTable.get(state).keySet()) {
                if(transitionTable.get(state).get(i).size() == 0) {
                    continue;
                }
                System.out.println(convertStateToString(state) + " -> " + convertStateToString(transitionTable.get(state).get(i)) + " [label=\"" + i + "\"];");
            }
        }
    }

    public static String convertStateToString(Set<Integer> state) {
        String s = "S";
        for(Integer i : state) {
          s += "_"+i;
        }
        return s;
    }

    public static void outDot2(Set<Integer>[][] transitionTable) {
        String finishNodes = "node [shape = doublecircle]; ";
        String startNodes = "node [shape = Mcircle]; ";
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");
        map.put(3, "-");

        System.out.println("rankdir=TB;");
        System.out.println("size=\"8,5\"");

        System.out.println("node [shape = circle];");
        for(int i = 0; i < 4; i++ ) {
            for (int j = 0; j < 4 ; j++) {
                if (transitionTable[i][j] != null) {
                    for (Integer s : transitionTable[i][j]) {
                        if(i == s &&  map.get(j).equals("-")) {
                            continue;
                        }
                        System.out.println("s" + i + " -> " + "s" + s + " [label=\"" + map.get(j) + "\"];");
                    }

                }
            }
        }
    }
}
