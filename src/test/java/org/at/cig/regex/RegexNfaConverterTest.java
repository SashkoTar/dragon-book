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
    public void shouldConcat() {
        Node s = buildSimpleGraphFor("s");
        Node t = buildSimpleGraphFor("t");
        assertTrue(getTransition(s, "s").isFinish());
        converter.concat(s, t);
        assertFalse(getTransition(s, "s").isFinish());
    }

    @Test
    public void shouldConcat2() {
        Node sStart = buildSimpleGraphFor("s");
        Node tStart = buildSimpleGraphFor("t");

        converter.concat(sStart, tStart);
        Node s = getTransition(sStart, "s");
        assertTrue(getTransition(s, "t").isFinish());
    }

    @Test
    public void shouldOr() {
        Node sStart = buildSimpleGraphFor("s");
        Node tStart = buildSimpleGraphFor("t");

        Node start = converter.or(sStart, tStart);
        assertEquals(2, start.getTransitions().size());

        Transition[] transitions = getEmptyTransitions(start.getTransitions());
        //q      ---------> sS --------------->S  ------->f
        Node beforeS = start.getTransitions().get(transitions[0]);
        Node s = getFirstTransition(beforeS);
        Node f = getFirstTransition(s);

        Node beforeT = start.getTransitions().get(transitions[0]);
        Node t = getFirstTransition(beforeT);
        Node f1 = getFirstTransition(t);

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
        Node startNode = Node.build();
        startNode.setStart(true);
        Transition a = new Transition(s);
        Node finishNode = Node.build();
        finishNode.setFinish(true);

        startNode.getTransitions().put(a, finishNode);
        return startNode;
    }
}
