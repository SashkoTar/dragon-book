package org.at.cig.common;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 5/16/15
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TransitionTable<K, V> {

    Set<V> getAlphabet();

    void addTransition(K stateT, V inputSymbol, K stateU);

    Map<V, K> forState(K state);

    K forState(K state, V inputSymbol);

    Object [] getStatesAsArray();

    Map<K, Map<V, K>> getTransitions();

    V getEpsilon();

    K getStartState();

    void setEpsilon(V epsilon);

    void setStartState(K startState);

}
