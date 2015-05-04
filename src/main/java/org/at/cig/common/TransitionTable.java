package org.at.cig.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by otarasenko on 5/4/15.
 */
public class TransitionTable {

    Map<State, Map<String, State>> dTransition = new HashMap<State, Map<String, State>>();

    public void addTransition(State T, String i, State U) {
        if(dTransition.containsKey(T)) {
            dTransition.get(T).put(i, U);
        }else {
            Map<String, State> transition = new HashMap<String, State>();
            transition.put(i, U);
            dTransition.put(T, transition);
        }
    }


    public void print() {
        for(State state : dTransition.keySet()) {
            System.out.println(state + "    " + dTransition.get(state));
        }
    }

}
