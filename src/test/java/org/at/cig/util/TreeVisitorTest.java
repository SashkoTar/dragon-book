package org.at.cig.util;

import org.at.cig.common.Node;
import org.at.cig.dsl.GraphBuilder;
import org.junit.Test;

/**
 * Created by otarasenko on 4/30/15.
 */
public class TreeVisitorTest {

    @Test
    public void shouldTraverseTree() {
        GraphBuilder builder = new GraphBuilder();
        Node treeRoot = builder
                .from("s0")
                .onEmpty().goTo("s1")
                .onEmpty().goTo("s2")
                .from("s1")
                .onEmpty().goTo("s3")
                .onEmpty().goTo("s4")
                .build();

        TreeVisitor visitor =  new TreeVisitor();
        visitor.visit(treeRoot);
    }

}
