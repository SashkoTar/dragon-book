package org.at.cig.common;

import java.util.*;


public class TransitionTableImpl<K,V> implements TransitionTable<K,V> {

    Set<V> alphabet = new HashSet<V>();
    Map<K, Map<V, K>> dTransition = new LinkedHashMap<K, Map<V, K>>();
    Map<K, K> emptyTransition = new LinkedHashMap<K, K>();
    V epsilon;
    K startState;

    @Override
    public void addTransition(K stateT, V inputSymbol, K stateU) {
        alphabet.add(inputSymbol);
        if(dTransition.containsKey(stateT)) {
            dTransition.get(stateT).put(inputSymbol, stateU);
        }else {
            Map<V, K> transition = new HashMap<V, K>();
            transition.put(inputSymbol, stateU);
            dTransition.put(stateT, transition);
        }
    }

    private Map<K, Map<V, K>> getdTransition() {
        return dTransition;
    }

    @Override
    public Map<V, K> forState(K state) {
        return dTransition.get(state);
    }

    @Override
    public K forState(K state, V inputSymbol) {
        return dTransition.get(state).get(inputSymbol);
    }

    @Override //TODO Cast to K
    public Object [] getStatesAsArray() {
        return getdTransition().keySet().toArray();
    }

    @Override
    public Map<K, Map<V, K>> getTransitions() {
        return dTransition;
    }


    @Override
    public Set<V> getAlphabet() {
        return alphabet;
    }

    @Override
    public V getEpsilon() {
        return epsilon;
    }

    @Override
    public K getStartState() {
        return startState;
    }

    public void setEpsilon(V epsilon) {
        this.epsilon = epsilon;
    }

    public void setStartState(K startState) {
        this.startState = startState;
    }

    public void print() {
        for(K state : dTransition.keySet()) {
            System.out.println(state + "    " + dTransition.get(state));
        }
    }


}
