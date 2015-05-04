package org.at.cig.common;


import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class StateHolderTest {

    State state;
    StateHolder holder;

    @Before
    public void init() {
        holder = new StateHolder();
        state = new State();
        state.add(1);
    }

    @Test
    public void shouldHaveUnmarked() {
        holder.add(state);
        assertTrue(holder.hasUnmarked());
    }

    @Test
    public void shouldDontHaveUnmarked() {
        holder.add(state);
        holder.next();
        assertFalse(holder.hasUnmarked());
    }

    @Test
    public void shouldContain() {
        holder.add(state);
        assertTrue(holder.contains(state));
    }

    @Test
    public void shouldAddAsSet() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        holder.add(set);
        assertTrue(holder.contains(state));
    }

    @Test
    public void shouldReturnNext() {
        holder.add(state);
        State expected = new State();
        expected.add(1);
        assertEquals(expected, holder.next());
    }

}
