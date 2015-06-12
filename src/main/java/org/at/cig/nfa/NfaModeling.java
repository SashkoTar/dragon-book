package org.at.cig.nfa;

import org.at.cig.common.State;
import org.at.cig.common.TransitionTable;
import org.at.cig.common.TransitionTableImpl;

import java.util.*;

// This class models NFA using algorithm 3.22
public class NfaModeling {


    TransitionTable<State, Object> nfaTransitionTable;

    private int [] inputString;// = {2,0,1};
    private int position = 0;

    private State finishState;

    public State getFinishState() {
        return finishState;
    }

    public void setFinishState(State finishState) {
        this.finishState = finishState;
    }




    public static void main(String[] args) {
        NfaModeling modeler = new NfaModeling();
        modeler.run();
    }

    public int[] getInputString() {
        return inputString;
    }

    public void setInputString(int[] inputString) {
        this.inputString = inputString;
    }

    public NfaModeling() {
        nfaTransitionTable = buildTransitionTable();
        finishState.add(3);
    }

    public void run() {
        model();
    }

    public boolean model() {
        Integer i = nextSymbol();
        State T = eClosure(buildSet(0));
        while (!isEndOfLine(i)) {
            T = eClosure(move(T, i));
            i = nextSymbol();
        }
        return isAnyFiniteState(T);
    }

    private boolean isAnyFiniteState(State s) {
        for(Object state : s) {
            if(finishState.contains(state)) {
                return true;
            }
        }
        return false;
    }


    private boolean isEndOfLine(Integer i) {
        return i < 0;
    }

    private int nextSymbol() {
        if(position == inputString.length) {
            return -1;
        }
        return inputString[position++];
    }


    private State move(State setOfStates, Object i) {
        State oneTransitionStates = new State();
        State state = new State();
        for (Object j : setOfStates) {
            state.clear();
            state.add(j);
            if (nfaTransitionTable.forState(state, i) != null) {
                oneTransitionStates.merge(nfaTransitionTable.forState(state, i));
            }
        }
        return oneTransitionStates;
    }



    public State eClosure(State states) {
        Set stateSet = new HashSet();
        Stack stack = new Stack();
        for (Object i : states) {
            stateSet.add(i);
            stack.push(i);
        }
        while (!stack.empty()) {
            Object t = stack.pop();
            Set nextStates = getEmptyTransitionsFromState(t);
            for (Object u : nextStates) {
                if (!stateSet.contains(u)) {
                    stateSet.add(u);
                    stack.push(u);
                }
            }
        }
        State result = new State();
        result.merge(stateSet);
        return result;
    }

    private Set getEmptyTransitionsFromState(Object i) {
        State state = new State();
        state.add(i);
        return nfaTransitionTable.forState(state, nfaTransitionTable.getEpsilon());
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



    private TransitionTable buildTransitionTable() {
        TransitionTable<State, Integer> table = new TransitionTableImpl<State, Integer>();
        State state0 = buildSet(0);
        State state1 = buildSet(1);
        State state2 = buildSet(2);
        State state3 = buildSet(3);

        table.setEpsilon(3);
        table.setStartState(state0);

        table.addTransition(state0, 0, state1);
        table.addTransition(state0, 2, state3);
        table.addTransition(state0, 3, state0);

        table.addTransition(state1, 1, state2);
        table.addTransition(state1, 3, buildSet(0,1));

        table.addTransition(state2, 0, state1);
        table.addTransition(state2, 3, state2);

        table.addTransition(state3, 2, state2);
        table.addTransition(state3, 3, buildSet(2,3));

        return table;
    }
    private State buildSet(Object... state) {
        State states = new State();
        for(Object i : state) {
            states.add(i);
        }
        return states;
    }




}
