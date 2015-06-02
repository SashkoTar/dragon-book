package org.at.cig.regex;

import org.at.cig.common.State;
import org.at.cig.common.TransitionTable;
import org.at.cig.common.TransitionTableImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RegexDfaConverterTest {

    @Test
    public void shouldConvertRegexToDfa() {
      RegexDfaConverter converter = new RegexDfaConverter();
        TransitionTable<State, String> transitionTable = converter.run();
        Object [] states =  transitionTable.getStatesAsArray();
        State stateA = (State) states[0];
        State stateB =(State) states[1];
        State stateC =(State) states[2];
        State stateD = (State)states[3];

        assertEquals(stateA, transitionTable.forState(stateA, "b"));
        assertEquals(stateB, transitionTable.forState(stateA, "a"));
        assertEquals(stateB, transitionTable.forState(stateB, "a"));
        assertEquals(stateC, transitionTable.forState(stateB, "b"));
        assertEquals(stateB, transitionTable.forState(stateC, "a"));
        assertEquals(stateD, transitionTable.forState(stateC, "b"));
        assertEquals(stateB, transitionTable.forState(stateD, "a"));
        assertEquals(stateA, transitionTable.forState(stateD, "b"));


   //     Printer.outDotTransitionTable(transitionTable);
    }
}
