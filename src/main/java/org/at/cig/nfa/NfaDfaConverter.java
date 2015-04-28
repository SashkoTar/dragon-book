package org.at.cig.nfa;


import java.util.*;
// This class converts NFA -> DFA using algorithm 3.20
public class NfaDfaConverter {

    private Set<Integer>[][] transitionTable;


    public static void main(String [] args) {
        NfaDfaConverter converter = new NfaDfaConverter();
        converter.run();
    }

    public NfaDfaConverter() {
        transitionTable = buildTransitionTable();
    }

    public void run() {
        Set<Integer> states =  buildSet(0);
       // print(e_closure(states));
        convert();
    }

    public void convert() {
        Stack<Set<Integer>> stack = new Stack<Set<Integer>>();
        Set<Set<Integer>> markedSets = new HashSet<Set<Integer>>();
        Map<Set<Integer>, Map<Integer, Set<Integer>>> dTransition = new HashMap<Set<Integer>, Map<Integer, Set<Integer>>>();
       // stack.push(e_closure(getInitialState()));
        stack.push(e_closure(buildSet(0)));
        while(!stack.empty()) {
            Set<Integer> T = stack.pop();
            markedSets.add(T);
            for(int i = 0; i < transitionTable[0].length-1; i++) {
                Set<Integer> U = e_closure(move(T, i));
              //  print("e_closure(move(T, i))", T, i, U);
                if(!markedSets.contains(U) && U.size() > 0 && stack.search(U) == -1) {
                    stack.push(U);
                }
                print("adding transition ", T, i, U);
                addTransition(dTransition, T, i, U);
            }
        }
        System.out.print(dTransition);
    }

    private Set<Integer> move(Set<Integer> T, int i) {
        Set<Integer> stateSet = new HashSet<Integer>();
        for(int state : T) {
          if(transitionTable[state][i] != null) {
              stateSet.addAll(transitionTable[state][i]);
          }
        }
        return stateSet;
    }

    private Map<Set<Integer>, Map<Integer, Set<Integer>>> addTransition(Map<Set<Integer>, Map<Integer, Set<Integer>>> dTransition, Set<Integer> T, Integer i, Set<Integer> U) {
        if(dTransition.containsKey(T)) {
            dTransition.get(T).put(i, U);
        }else {
            Map<Integer, Set<Integer>> transition = new HashMap<Integer, Set<Integer>>();
            transition.put(i, U);
            dTransition.put(T, transition);
        }
        return dTransition;
    }


    public Set<Integer> e_closure(Set<Integer> states) {
        Set<Integer> stateSet = new HashSet<Integer>();
        Stack<Integer> stack = new Stack<Integer>() ;
        for(int i : states) {
            stateSet.add(i);
            stack.push(i);
        }
        while (!stack.empty()) {
            int t = stack.pop();
            Set<Integer> nextStates = transitionTable[t][transitionTable[0].length-1];
            for(int u : nextStates) {
                if(!stateSet.contains(u)) {
                    stateSet.add(u);
                    stack.push(u);
                }
            }
        }

        return stateSet;
    }

    private void print(String message, Set<Integer> states) {
        System.out.print(message + ": ");
        for(int i : states) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }

    private void print(String message, Set<Integer> states, int i, Set<Integer> nextState) {
        System.out.print(message + ": {");
        for(int j : states) {
            System.out.print(j + " ");
        }
        System.out.print("} by " + i + "  ===> {");
        for(int j : nextState) {
            System.out.print(j + " ");
        }
        System.out.print(" }");
        System.out.println("");
    }

    public Set<Integer>[][] buildTransitionTable() {
        Set<Integer>[][] tt = new HashSet[4][4];
        //----ROW  1 -------
        tt[0][0] = buildSet(1);
        tt[0][1] = null;
        tt[0][2] = buildSet(3);
        tt[0][3] = buildSet(0);
//----ROW  2 -------
        tt[1][0] = null;
        tt[1][1] = buildSet(2);
        tt[1][2] = null;
        tt[1][3] = buildSet(0,1);
//----ROW  2 -------
        tt[2][0] = buildSet(1);
        tt[2][1] = null;
        tt[2][2] = null;
        tt[2][3] = buildSet(2);

//----ROW  2 -------
        tt[3][0] = null;
        tt[3][1] = null;
        tt[3][2] = buildSet(2);
        tt[3][3] = buildSet(2,3);

        return tt;
    }

    public Set<Integer> buildSet(Integer... state) {
        Set<Integer> states = new HashSet<Integer>();
        for(int i : state) {
            states.add(i);
        }
        return states;
    }


}