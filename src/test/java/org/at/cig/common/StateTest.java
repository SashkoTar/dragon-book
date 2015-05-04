package org.at.cig.common;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by otarasenko on 5/4/15.
 */
public class StateTest {

    @Test
    public void shouldBeEqual() {
        State expected = new State();
        expected.add(1);
        expected.add(2);

        State actual = new State();
        actual.add(2);
        actual.add(1);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMerge() {
        State expected = new State();
        expected.add(1);
        expected.add(2);
        expected.add(3);

        State actual = new State();
        actual.add(2);
        actual.add(1);

        State toBeMerged = new State();
        toBeMerged.add(3);

        actual.merge(toBeMerged);

        assertEquals(expected, actual);
    }

}
