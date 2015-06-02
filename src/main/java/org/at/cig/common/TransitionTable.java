package org.at.cig.common;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 5/16/15
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TransitionTable<K, V> {
    void addTransition(K stateT, V inputSymbol, K stateU);

    Map<V, K> forState(K state);

    K forState(K state, V inputSymbol);

    Object [] getStatesAsArray();
}
