package org.at.cig.util;

import org.at.cig.regex.RegexNfaConverter;
import org.at.cig.common.Node;
import org.at.cig.common.Transition;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/26/15
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrinterTest {

    private RegexNfaConverter converter;

    @Before
    public void init() {
        this.converter = new RegexNfaConverter();
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
