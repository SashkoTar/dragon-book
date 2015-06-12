package org.at.cig.nfa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public void shouldAcceptString() {
        modeling.setInputString(new int [] {0,1,0,2});
        Assert.assertTrue(modeling.model());
    }


    @Test
    public void shouldAcceptStringWithNonDefault() {
        modeling.finishState.clear();
        modeling.finishState.add(2);
        modeling.setInputString(new int [] {0,1});
        Assert.assertTrue(modeling.model());
    }



}
