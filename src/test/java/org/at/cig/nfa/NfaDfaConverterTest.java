package org.at.cig.nfa;

import org.at.cig.common.State;
import org.at.cig.common.TransitionTable;
import org.at.cig.common.TransitionTableImpl;
import org.at.cig.util.Printer;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class NfaDfaConverterTest {

    @Test
    public void shouldConvertNfaToDfa() {
        NfaDfaConverter converter = new NfaDfaConverter(buildTransitionTable());
        TransitionTable<State, Integer> dfa = converter.run();

        State stateA = buildSet(0);
        State stateB = buildSet(2,3);
        State stateC = buildSet(2);
        State stateD = buildSet(0, 1);

        Assert.assertEquals(stateB, dfa.forState(stateA, 2));
        Assert.assertEquals(stateD, dfa.forState(stateA, 0));

        Assert.assertEquals(stateD, dfa.forState(stateB, 0));
        Assert.assertEquals(stateC, dfa.forState(stateB, 2));

        Assert.assertEquals(stateD, dfa.forState(stateC, 0));

        Assert.assertEquals(stateD, dfa.forState(stateD, 0));
        Assert.assertEquals(stateC, dfa.forState(stateD, 1));
        Assert.assertEquals(stateB, dfa.forState(stateD, 2));
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

    @Test
    public void shouldConvertNfaToDfa2() {
        NfaDfaConverter converter = new NfaDfaConverter(buildTransitionTable2());
        TransitionTable<State, String> dfa = converter.run();

        State stateA = buildSet("0");
        State stateB = buildSet("2","3");
        State stateC = buildSet("2");
        State stateD = buildSet("0", "1");

        Assert.assertEquals(stateB, dfa.forState(stateA, "2"));
        Assert.assertEquals(stateD, dfa.forState(stateA, "0"));

        Assert.assertEquals(stateD, dfa.forState(stateB, "0"));
        Assert.assertEquals(stateC, dfa.forState(stateB, "2"));

        Assert.assertEquals(stateD, dfa.forState(stateC, "0"));

        Assert.assertEquals(stateD, dfa.forState(stateD, "0"));
        Assert.assertEquals(stateC, dfa.forState(stateD, "1"));
        Assert.assertEquals(stateB, dfa.forState(stateD, "2"));
    }

    private TransitionTable buildTransitionTable2() {
        TransitionTable<State, String> table = new TransitionTableImpl<State, String>();

        State state0 = buildSet("0");
        State state1 = buildSet("1");
        State state2 = buildSet("2");
        State state3 = buildSet("3");

        table.setEpsilon("3");
        table.setStartState(state0);

        table.addTransition(state0, "0", state1);
        table.addTransition(state0, "2", state3);
        table.addTransition(state0, "3", state0);

        table.addTransition(state1, "1", state2);
        table.addTransition(state1, "3", buildSet("0","1"));

        table.addTransition(state2, "0", state1);
        table.addTransition(state2, "3", state2);

        table.addTransition(state3, "2", state2);
        table.addTransition(state3, "3", buildSet("2","3"));

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
