package org.at.cig.regex;

import org.at.cig.common.Fragment;
import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;


public class RegexNfaConverterTest {


    private RegexNfaConverter converter;

    @Before
    public void init() {
        this.converter = new RegexNfaConverter();
        Node.getNodes().clear();
    }



    @Test
    public void shouldHandleOperand() {
        Fragment fragment = converter.operand("s");
        Node outS = fragment.getStartNode();
        assertEquals(1, outS.getTransitions().size());
        assertTrue(isStrictlyStart(outS));

        Node inS = getFirstTransition(outS);
        assertEquals(inS, fragment.getTailNode());
        assertTrue(isStrictlyFinish(inS));
    }


    @Test
    public void shouldHaveTwoNodesInGraphAfterOperandCall() {
        converter.operand("s");
        assertEquals(2, Node.getNodes().size());
    }

    @Test
    public void shouldHaveOneTransitionInGraphAfterOperandCall() {
        converter.operand("s");
        assertEquals(1, calculateTotalTransitionNumber());
    }

    private int calculateTotalTransitionNumber() {
        int totalTransitionNumber = 0;
        for(Node node : Node.getNodes()) {
            totalTransitionNumber += node.getTransitions().size();
        }
        return totalTransitionNumber;
    }


    @Test
    public void shouldConcat() {
        Fragment sF = buildSimpleFragmentFor("s");
        Fragment tF = buildSimpleFragmentFor("t");

        sF = converter.concat(sF, tF);

        Node outS = sF.getStartNode();
        assertTrue(isStrictlyStart(outS));
        Node inS = getTransition(outS, "s");
        assertTrue("Non internal", isStrictlyInternal(inS));
        Node inT = getTransition(inS, "t");
        assertTrue(isStrictlyFinish(inT));
        assertEquals(sF.getTailNode(), inT);
    }

    @Test
    public void shouldHaveTreeNodesInGraphAfterConcatCall() {
        Fragment sF = buildSimpleFragmentFor("s");
        Fragment tF = buildSimpleFragmentFor("t");

        converter.concat(sF, tF);
        assertEquals(3, Node.getNodes().size());
    }

    @Test
    public void shouldHaveTwoTransitionsInGraphAfterOperandCall() {
        Fragment sF = buildSimpleFragmentFor("s");
        Fragment tF = buildSimpleFragmentFor("t");

        converter.concat(sF, tF);
        assertEquals(2, calculateTotalTransitionNumber());
    }

    @Test
    public void shouldOr() {
        Fragment sF = buildSimpleFragmentFor("s");
        Fragment tF = buildSimpleFragmentFor("t");

        sF = converter.or(sF, tF);
        Node start = sF.getStartNode();
        assertEquals(2, start.getTransitions().size());
        assertTrue(isStrictlyStart(start));

        Transition[] transitions = getEmptyTransitions(start.getTransitions());
        //q      ---------> sS --------------->S  ------->f

        Node outS = start.getTransitions().get(transitions[0]);
        assertTrue(isStrictlyInternal(outS));
        Node inS = getFirstTransition(outS);
        assertTrue(isStrictlyInternal(inS));
        Node f = getFirstTransition(inS);
        assertTrue(isStrictlyFinish(f));

        Node beforeT = start.getTransitions().get(transitions[1]);
        assertTrue(isStrictlyInternal(beforeT));

        Node t = getFirstTransition(beforeT);
        assertTrue(isStrictlyInternal(t));

        Node f1 = getFirstTransition(t);
        assertTrue(isStrictlyFinish(f1));

        assertTrue(f == f1);
        assertEquals(sF.getTailNode(), f);
    }

    @Test
    public void shouldHaveSixTransitionsInGraphAfterOrCall() {
        Fragment sF = buildSimpleFragmentFor("s");
        Fragment tF = buildSimpleFragmentFor("t");

        sF = converter.or(sF, tF);
        assertEquals(6, calculateTotalTransitionNumber());
    }

    @Test
    public void shouldHaveSixNodesInGraphAfterOrCall() {
        Fragment sF = buildSimpleFragmentFor("s");
        Fragment tF = buildSimpleFragmentFor("t");

        converter.or(sF, tF);
        assertEquals(6, Node.getNodes().size());
    }

    @Test
    public void shouldStar() {
        Fragment sF = buildSimpleFragmentFor("s");

        sF = converter.star(sF);
        Node startNode = sF.getStartNode();

        Transition[] transitions = getEmptyTransitions(startNode.getTransitions());
        assertEquals(2, transitions.length);
        Node finish;
        Node internal;
        if (startNode.getTransitions().get(transitions[0]).isFinish()) {
            finish = startNode.getTransitions().get(transitions[0]);
            internal = startNode.getTransitions().get(transitions[1]);
        } else {
            finish = startNode.getTransitions().get(transitions[1]);
            internal = startNode.getTransitions().get(transitions[0]);
        }

        Node s = getTransition(internal, "s");
        assertNotNull(s);
        transitions = getEmptyTransitions(s.getTransitions());
        assertEquals(2, transitions.length);

        Node finish1;
        Node internal1;
        if (s.getTransitions().get(transitions[0]).isFinish()) {
            finish1 = s.getTransitions().get(transitions[0]);
            internal1 = s.getTransitions().get(transitions[1]);
        } else {
            finish1 = s.getTransitions().get(transitions[1]);
            internal1 = s.getTransitions().get(transitions[0]);
        }
        assertTrue(finish == finish1);
        assertTrue(internal == internal1);
        assertEquals(sF.getTailNode(), finish);
    }

    @Test
    public void shouldHaveFourNodesInGraphAfterStarCall() {
        Fragment sF = buildSimpleFragmentFor("s");

        converter.star(sF);
        assertEquals(4, Node.getNodes().size());
    }

    @Test
    public void shouldHaveFiveTransitionsInGraphAfterStarCall() {
        Fragment sF = buildSimpleFragmentFor("s");

        converter.star(sF);
        assertEquals(5, calculateTotalTransitionNumber());
    }

    @Test
    public void shouldHandleString() {
        Fragment node = converter.convert("ab.");
        Node s = getTransition(node.getStartNode(), "a");
        assertTrue(getTransition(s, "b").isFinish());
    }

    private boolean isStrictlyStart(Node node) {
        return node.isStart() && !node.isFinish();
    }

    private boolean isStrictlyFinish(Node node) {
        return !node.isStart() && node.isFinish();
    }

    private boolean isStrictlyInternal(Node node) {
        return !node.isStart() && !node.isFinish();
    }

    private Transition[] getEmptyTransitions(Map<Transition, Node> transitions) {
        return transitions.keySet().toArray(new Transition[2]);
    }

    private Node getFirstTransition(Node node) {
        for (Transition transition : node.getTransitions().keySet()) {
            return node.getTransitions().get(transition);
        }
        return null;
    }

    private Node getTransition(Node node, String s) {
        for (Transition transition : node.getTransitions().keySet()) {
            if (transition.getSymbol() != null && transition.getSymbol().equals(s)) {
                return node.getTransitions().get(transition);
            }
        }
        return null;
    }


    private Fragment buildSimpleFragmentFor(String s) {
        return converter.operand(s);
    }
}
