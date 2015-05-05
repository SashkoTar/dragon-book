package org.at.cig.util;

import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.at.cig.dsl.TreeBuilder;
import org.junit.Test;

import java.util.Stack;


public class TreeBuilderTest {


    // @Test
    public void shouldBuildAndPrintGraph() {
        TreeBuilder builder = new TreeBuilder();
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

        print();
    }

    //   @Test
    public void shouldBuildAndPrintGraph2() {
        TreeBuilder builder = new TreeBuilder();
        builder
                .fromRoot("s0")
                .addCatChild("s1")
                .addLeafChild("#")
                .from("s1")
                .addLeafChild("a")
                .addLeafChild("b")

                .build();

        print();
    }



    private void print() {
        Printer.out(Node.getNodes());
    }
}
