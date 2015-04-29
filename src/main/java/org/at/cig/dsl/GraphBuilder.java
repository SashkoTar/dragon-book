package org.at.cig.dsl;

import org.at.cig.common.Node;

/**
 * Created by otarasenko on 4/29/15.
 */
public class GraphBuilder {

    Node node;

    public GraphBuilder from(String nodeName) {
        node = Node.build(nodeName);
        return this;
    }

    public GraphBuilder on(String symbol) {
        return this;
    }

    public GraphBuilder goTo(String nodeName) {
        return this;
    }

    public Node build() {
        return node;
    }
}
