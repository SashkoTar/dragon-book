package org.at.cig.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by otarasenko on 5/4/15.
 */
public class StateHolder {

    private List<State> states = new ArrayList<State>();
    private int currentIndex = 0;


    public boolean hasUnmarked() {
        return currentIndex < states.size();
    }

    public State next() {
        return states.get(currentIndex++);
    }

    public boolean contains(State state) {
        return states.contains(state);
    }

    public void add(State state) {
        states.add(state);
    }

    public void add(Set<Integer> integers) {
        State state = new State();
        state.merge(integers);
        states.add(state);
    }
}
