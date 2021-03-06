package org.at.cig.util;

import org.at.cig.common.Node;
import org.at.cig.dsl.GraphBuilder;
import org.at.cig.dsl.TreeBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by otarasenko on 4/30/15.
 */
public class TreeVisitorTest {

    Node treeRoot;
    TreeVisitor visitor;

    @Before
    public void init() {
        TreeBuilder builder = new TreeBuilder();
        treeRoot =
        builder
                .fromRoot("s0")
                .addCatChild("s1")
                .addLeafChild("#")
                .from("s1")
                .addCatChild("s2")
                .addLeafChild("b")
                .from("s2")
                .addCatChild("s3")
                .addLeafChild("b")
                .from("s3")
                .addStarChild("s4")
                .addLeafChild("a")
                .from("s4")
                .addOrChild("s5")
                .from("s5")
                .addLeafChild("a")
                .addLeafChild("b")
                .build();

         visitor =  new TreeVisitor();
    }

    @Test
    public void shouldTraverseTree() {
        visitor.run(treeRoot);
    }
}
