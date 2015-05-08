package org.at.cig.regex;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.*;


public class RegexNfaConverterTest {


    private RegexNfaConverter converter;

    @Before
    public void init() {
        this.converter = new RegexNfaConverter();
    }

    @Test
    public void shouldHandleOperand() {
       Node sNode = converter.operand("s");
       assertEquals(1, sNode.getTransitions().size());
       assertTrue(isStrictlyStart(sNode));
       Node tNode = getFirstTransition(sNode);
       assertTrue(isStrictlyFinish(tNode));
    }

    @Test
    public void shouldConcat() {
        Node s = buildSimpleGraphFor("s");
        Node t = buildSimpleGraphFor("t");

        converter.concat(s, t);
        assertTrue(isStrictlyStart(s));
        Node internalNode = getTransition(s, "s");
        assertTrue(isStrictlyInternal(internalNode));
        Node fNode = getTransition(internalNode, "t");
        assertTrue(isStrictlyFinish(fNode));
    }

    @Test
    public void shouldOr() {
        Node sStart = buildSimpleGraphFor("s");
        Node tStart = buildSimpleGraphFor("t");

        Node start = converter.or(sStart, tStart);
        assertEquals(2, start.getTransitions().size());
        assertTrue(isStrictlyStart(start));

        Transition[] transitions = getEmptyTransitions(start.getTransitions());
        //q      ---------> sS --------------->S  ------->f


        Node beforeS = start.getTransitions().get(transitions[0]);
        assertTrue(isStrictlyInternal(beforeS));
        Node s = getFirstTransition(beforeS);
        assertTrue(isStrictlyInternal(s));
        Node f = getFirstTransition(s);
        assertTrue(isStrictlyFinish(f));

        Node beforeT = start.getTransitions().get(transitions[1]);
        assertTrue(isStrictlyInternal(beforeT));

        Node t = getFirstTransition(beforeT);
        assertTrue(isStrictlyInternal(t));

        Node f1 = getFirstTransition(t);
        assertTrue(isStrictlyFinish(f1));


        assertTrue(f == f1);
    }

    @Test
    public void shouldStar() {
        Node sStart = buildSimpleGraphFor("s");
        Node startNode = converter.star(sStart);
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
    }

    @Test
    public void shouldSetCorrectTypesForStar() {
        Node sStart = buildSimpleGraphFor("s");
        Node startNode = converter.star(sStart);
        assertTrue(isStrictlyStart(startNode));
        Transition[] transitions = getEmptyTransitions(startNode.getTransitions());
        Node fNode = startNode.getTransitions().get(transitions[0]);
        Node next;
        if(fNode.getTransitions().size() == 0) {
            next = startNode.getTransitions().get(transitions[1]);
        } else {
            next = startNode.getTransitions().get(transitions[0]);
            fNode = startNode.getTransitions().get(transitions[1]);
        }
        assertTrue(isStrictlyFinish(fNode));
        assertTrue(isStrictlyInternal(next));
        Node s = getTransition(next, "s");
        assertTrue(isStrictlyInternal(s));

    }

    @Test
    public void shouldHandleString() {
        Node node = converter.convert("ab.");
        Node s = getTransition(node, "a");
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

    private Node buildSimpleGraphFor(String s) {
        return converter.operand(s);
    }
}
