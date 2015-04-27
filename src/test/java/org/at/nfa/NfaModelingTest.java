package org.at.nfa;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/6/15
 * Time: 11:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class NfaModelingTest {

    NfaModeling modeling;

    @Before
    public void init() {
        modeling = new NfaModeling();
    }

    @Test
    public void shouldCheckNext() {
        assertEquals(2, modeling.nextSymbol());
        assertEquals(0, modeling.nextSymbol());
        assertEquals(1, modeling.nextSymbol());
        assertEquals(-1, modeling.nextSymbol());
    }


    @Test
    public void shouldFindFiniteState() {
        Set<Integer> S = new HashSet<Integer>();
        S.add(2);
        S.add(1);
        S.add(0);
        S.add(3);
        assertTrue(modeling.isAnyFiniteState(S));
    }

    @Test
    public void shouldNotFindFiniteState() {
        Set<Integer> S = new HashSet<Integer>();
        S.add(2);
        S.add(1);
        S.add(0);
        S.add(5);
        assertFalse(modeling.isAnyFiniteState(S));
    }

}
