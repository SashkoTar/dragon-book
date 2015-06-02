package org.at.cig.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class TransitionTableImpl<K,V> implements TransitionTable<K,V> {

    Map<K, Map<V, K>> dTransition = new LinkedHashMap<K, Map<V, K>>();
    Map<K, K> emptyTransition = new LinkedHashMap<K, K>();

    @Override
    public void addTransition(K stateT, V inputSymbol, K stateU) {
        if(dTransition.containsKey(stateT)) {
            dTransition.get(stateT).put(inputSymbol, stateU);
        }else {
            Map<V, K> transition = new HashMap<V, K>();
            transition.put(inputSymbol, stateU);
            dTransition.put(stateT, transition);
        }
    }

    public Map<K, Map<V, K>> getdTransition() {
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

    public void print() {
        for(K state : dTransition.keySet()) {
            System.out.println(state + "    " + dTransition.get(state));
        }
    }

}
