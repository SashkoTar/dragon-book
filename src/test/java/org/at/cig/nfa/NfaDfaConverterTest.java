package org.at.cig.nfa;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/6/15
 * Time: 11:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class NfaDfaConverterTest {

    @Test
    public void setShouldBeEqual() {
        Set<Integer> set1 = new HashSet<Integer>();
        set1.add(1);
        set1.add(3);
        set1.add(14);

        Set<Integer> set2 = new HashSet<Integer>();
        set2.add(3);
        set2.add(1);
        set2.add(14);

        assertEquals(set1, set2);

    }

    @Test
    public void shouldContain() {
        Set<Integer> set1 = new HashSet<Integer>();
        set1.add(1);
        set1.add(3);
        set1.add(14);
        Set<Set<Integer>> markedSets = new HashSet<Set<Integer>>();
        markedSets.add(set1);


        Set<Integer> set2 = new HashSet<Integer>();
        set2.add(2);
        set2.add(1);
        set2.add(14);

        assertTrue(markedSets.contains(set2));

    }

}
