package org.at.nfa;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/20/15
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegExpConverterTest {

    @Test
    public void shouldGenerate() {
        String regExp = "ab";
        RegExpConverter converter = new RegExpConverter();
        Node node = converter.convert(null, regExp);
        assertEquals(2, node.children.size());
        assertEquals('a', node.children.get(0).children.get(0).value);
        assertEquals('b', node.children.get(1).children.get(0).value);
    }

    @Test
    public void shouldGenerateForOr() {
        String regExp = "a|b";
        RegExpConverter converter = new RegExpConverter();
        Node node = converter.convert(null,regExp);
        assertEquals(3, node.children.size());
        assertEquals('a', node.children.get(0).children.get(0).value);
        assertEquals('|', node.children.get(1).value);
        assertEquals('b', node.children.get(2).children.get(0).value);
    }

    @Test
    public void shouldGenerateForKleeneStar() {
        String regExp = "a*";
        RegExpConverter converter = new RegExpConverter();
        Node node = converter.convert(null, regExp);
        assertEquals(2, node.children.size());
        assertEquals('a', node.children.get(0).children.get(0).value);
        assertEquals('*', node.children.get(1).value);
    }

    @Test
    public void shouldGenerate3Concat() {
        String regExp = "abc";
        RegExpConverter converter = new RegExpConverter();
        Node node = converter.convert(null, regExp);
        assertEquals(2, node.children.size());
       // assertEquals('a', node.children.get(0).children.get(0).value);
        assertEquals('c', node.children.get(1).children.get(0).value);
    }
}
