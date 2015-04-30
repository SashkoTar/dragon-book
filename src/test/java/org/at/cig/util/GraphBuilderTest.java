package org.at.cig.util;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.at.cig.dsl.GraphBuilder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by otarasenko on 4/29/15.
 */
public class GraphBuilderTest {

    @Test
    public void shouldBuildGraphWithTwoStatesAndOneTransition() {
        GraphBuilder builder = new GraphBuilder();
        Node start = builder
                .from("s0")
                .on("a").goTo("s1")
                .build();
        assertEquals("s0", start.getName());
        assertEquals(1, start.getTransitions().size());
        Transition transition = (Transition) start.getTransitions().keySet().toArray()[0];
        assertEquals("a", transition.getSymbol());
        assertEquals("s1", start.getTransitions().get(transition).getName());
    }

    @Test
    public void shouldBuildGraphWithTwoStatesAndTwoTransition() {
        GraphBuilder builder = new GraphBuilder();
        Node start = builder
                .from("s0")
                .on("a").goTo("s1")
                .on("b").goTo("s1")
                .build();
        assertEquals(2, Node.getNodes().size());
        assertEquals("s0", start.getName());
        assertEquals(2, start.getTransitions().size());
        Transition transitionA = (Transition) start.getTransitions().keySet().toArray()[0];
        Transition transitionB = (Transition) start.getTransitions().keySet().toArray()[1];
        assertEquals("a", transitionA.getSymbol());
        assertEquals("b", transitionB.getSymbol());
        assertEquals("s1", start.getTransitions().get(transitionA).getName());
        assertEquals("s1", start.getTransitions().get(transitionB).getName());

    }

    @Test
    public void shouldBuildGraphWithTreeStatesAndTwoTransition() {
        GraphBuilder builder = new GraphBuilder();
        Node s0 = builder
                .from("s0")
                .on("a").goTo("s1")
                .from("s1")
                .on("b").goTo("s2")
                .build();
        assertEquals("s0", s0.getName());
        assertEquals(1, s0.getTransitions().size());
        Transition transition = (Transition) s0.getTransitions().keySet().toArray()[0];
        assertEquals("a", transition.getSymbol());
        Node s1 = s0.getTransitions().get(transition);
        assertEquals("s1", s1.getName());

        transition = (Transition) s1.getTransitions().keySet().toArray()[0];
        assertEquals("b", transition.getSymbol());
        Node s2 = s1.getTransitions().get(transition);
        assertEquals("s2", s2.getName());
    }


    @Test
    public void shouldBuildGraphWithTwoStatesWithOneEmptyTransition() {
        GraphBuilder builder = new GraphBuilder();
        Node s0 = builder
                .from("s0")
                .on("a").goTo("s1")
                .from("s1")
                .onEmpty().goTo("s2")
                .build();
        assertEquals("s0", s0.getName());
        assertEquals(1, s0.getTransitions().size());
        Transition transition = (Transition) s0.getTransitions().keySet().toArray()[0];
        assertEquals("a", transition.getSymbol());
        Node s1 = s0.getTransitions().get(transition);
        assertEquals("s1", s1.getName());

        transition = (Transition) s1.getTransitions().keySet().toArray()[0];
        assertTrue(transition.isEmty());
        Node s2 = s1.getTransitions().get(transition);
        assertEquals("s2", s2.getName());
    }

    @Test
    public void shouldBuildGraphWithTwoStatesWithDiffStateTypes() {
        GraphBuilder builder = new GraphBuilder();
        Node s0 = builder
                .from("s0")
                .on("a").goTo("s1")
                .from("s1")
                .onEmpty().goToFinal("s2")
                .build();
        assertEquals("s0", s0.getName());
        assertEquals(1, s0.getTransitions().size());
        Transition transition = (Transition) s0.getTransitions().keySet().toArray()[0];
        assertEquals("a", transition.getSymbol());
        Node s1 = s0.getTransitions().get(transition);
        assertEquals("s1", s1.getName());

        transition = (Transition) s1.getTransitions().keySet().toArray()[0];
        assertTrue(transition.isEmty());
        Node s2 = s1.getTransitions().get(transition);
        assertEquals("s2", s2.getName());
        assertTrue(s2.isFinish());
    }

    @Test
    public void shouldBuildAndPrintGraph() {
        GraphBuilder builder = new GraphBuilder();
        builder
                .from("s0")
                    .on("a").goTo("s1")
                    .on("d").goTo("s3")
                .from("s1")
                    .on("b").goTo("s2")
                .from("s3")
                    .on("b").goTo("s1")
                .build();
    }

    private void print() {
        Printer.out(Node.getNodes());
    }

}
