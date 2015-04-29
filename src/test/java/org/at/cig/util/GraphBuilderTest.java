package org.at.cig.util;

import org.at.cig.common.Node;
import org.at.cig.dsl.GraphBuilder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by otarasenko on 4/29/15.
 */
public class GraphBuilderTest {

    @Test
    public void shouldBuildGraphWithTwoStates() {
        GraphBuilder builder = new GraphBuilder();
        Node start = builder
                .from("s0")
                .on("a").goTo("s1")
                .build();
        assertEquals("s0", start.getName());
        assertEquals(1, start.getTransitions().size());
    }
}
