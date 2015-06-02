package org.at.cig.nfa;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;


public class NfaDfaConverterTest {

    @Test
    public void shouldConvertRegexToDfa() {
        NfaDfaConverter converter = new NfaDfaConverter();
        Map<Set<Integer>, Map<Integer, Set<Integer>>> dTransition = converter.run().getdTransition();
        Set [] states = dTransition.keySet().toArray(new Set[4]);
        Set stateA = states[0];
        Set stateB = states[1];
        Set stateC = states[2];
        Set stateD = states[3];

        Assert.assertEquals(stateB, dTransition.get(stateA).get(2));
        Assert.assertEquals(stateD, dTransition.get(stateA).get(0));

        Assert.assertEquals(stateD, dTransition.get(stateB).get(0));
        Assert.assertEquals(stateC, dTransition.get(stateB).get(2));

        Assert.assertEquals(stateD, dTransition.get(stateC).get(0));

        Assert.assertEquals(stateD, dTransition.get(stateD).get(0));
        Assert.assertEquals(stateC, dTransition.get(stateD).get(1));
        Assert.assertEquals(stateB, dTransition.get(stateD).get(2));


        //     Printer.outDotTransitionTable(transitionTable);
    }

}
