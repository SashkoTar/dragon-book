package org.at.cig.nfa;

import java.util.*;

// This class models NFA using algorithm 3.22
public class NfaModeling {


    private Set<Integer>[][] transitionTable;

    private int [] inputString = {2,0,1};
    private int position = 0;
    private Set<Integer> F = new HashSet<Integer>();


    public static void main(String[] args) {
        NfaModeling modeler = new NfaModeling();
        modeler.run();
    }

    public NfaModeling() {
        transitionTable = buildTransitionTable();
        F.add(3);
    }

    public void run() {
        model();
    }

    public boolean model() {
        Integer i = nextSymbol();
        Set<Integer> T = e_closure(buildSet(0));
        while (!isEndOfLine(i)) {
            T = e_closure(move(T, i));
            i = nextSymbol();
        }
        if(isAnyFiniteState(T)) {
            return true;
        }
        return false;
    }

    public boolean isAnyFiniteState(Set<Integer> s) {
        for(Integer state : s) {
            if(F.contains(state)) {
                return true;
            }
        }
        return false;
    }


    private boolean isEndOfLine(Integer i) {
        return i < 0;
    }

    public int nextSymbol() {
        if(position == inputString.length) {
            return -1;
        }
        return inputString[position++];
    }


    private Set<Integer> move(Set<Integer> T, int i) {
        Set<Integer> stateSet = new HashSet<Integer>();
        for (int state : T) {
            if (transitionTable[state][i] != null) {
                stateSet.addAll(transitionTable[state][i]);
            }
        }
        return stateSet;
    }

    private Map<Set<Integer>, Map<Integer, Set<Integer>>> addTransition(Map<Set<Integer>, Map<Integer, Set<Integer>>> dTransition, Set<Integer> T, Integer i, Set<Integer> U) {
        if (dTransition.containsKey(T)) {
            dTransition.get(T).put(i, U);
        } else {
            Map<Integer, Set<Integer>> transition = new HashMap<Integer, Set<Integer>>();
            transition.put(i, U);
            dTransition.put(T, transition);
        }
        return dTransition;
    }


    public Set<Integer> e_closure(Set<Integer> states) {
        Set<Integer> stateSet = new HashSet<Integer>();
        Stack<Integer> stack = new Stack<Integer>();
        for (int i : states) {
            stateSet.add(i);
            stack.push(i);
        }
        while (!stack.empty()) {
            int t = stack.pop();
            Set<Integer> nextStates = transitionTable[t][transitionTable[0].length - 1];
            for (int u : nextStates) {
                if (!stateSet.contains(u)) {
                    stateSet.add(u);
                    stack.push(u);
                }
            }
        }

        return stateSet;
    }

    private void print(String message, Set<Integer> states) {
        System.out.print(message + ": ");
        for (int i : states) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }

    private void print(String message, Set<Integer> states, int i, Set<Integer> nextState) {
        System.out.print(message + ": {");
        for (int j : states) {
            System.out.print(j + " ");
        }
        System.out.print("} by " + i + "  ===> {");
        for (int j : nextState) {
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
        tt[1][3] = buildSet(0, 1);
//----ROW  2 -------
        tt[2][0] = buildSet(1);
        tt[2][1] = null;
        tt[2][2] = null;
        tt[2][3] = buildSet(2);

//----ROW  2 -------
        tt[3][0] = null;
        tt[3][1] = null;
        tt[3][2] = buildSet(2);
        tt[3][3] = buildSet(2, 3);

        return tt;
    }

    public Set<Integer> buildSet(Integer... state) {
        Set<Integer> states = new HashSet<Integer>();
        for (int i : state) {
            states.add(i);
        }
        return states;
    }


}
