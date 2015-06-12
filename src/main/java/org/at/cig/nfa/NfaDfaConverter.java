package org.at.cig.nfa;


import org.at.cig.common.State;
import org.at.cig.common.StateHolder;
import org.at.cig.common.TransitionTable;
import org.at.cig.common.TransitionTableImpl;

import java.util.*;

// This class converts NFA -> DFA using algorithm 3.20
public class NfaDfaConverter {

    TransitionTable<State, Object> nfaTransitionTable;
    TransitionTable<State, Object> dfaTransitionTable = new TransitionTableImpl<State, Object>();
    private StateHolder dStates = new StateHolder();

    public NfaDfaConverter(TransitionTable table) {
        nfaTransitionTable = table;
    }

    public TransitionTable run() {
        convert();
        return dfaTransitionTable;
    }

    public void convert() {
        initDStates();
        while (dStates.hasUnmarked()) {
            State T = dStates.next();
            for (Object i : getAlphabet()) {
                State U = eClosure(move(T, i));
                if (!dStates.contains(U)) {
                    dStates.add(U);
                }
                dfaTransitionTable.addTransition(T, i, U);
            }
        }
    }

    private void initDStates() {
        dStates.add(eClosure(nfaTransitionTable.getStartState()));
    }

    private Set<Object> getAlphabet() {
        return nfaTransitionTable.getAlphabet();
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


}
