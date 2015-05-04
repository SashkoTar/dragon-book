package org.at.cig.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by otarasenko on 4/27/15.
 */
public class InfixPostfixConverterTest {

    @Test
    public void shouldConvert() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab.", converter.handle("ab"));
    }

    @Test
    public void shouldConvert2() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("abc.|", converter.handle("a|bc"));
    }

    @Test
    public void shouldConvert3() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab.c|", converter.handle("ab|c"));
    }

    @Test
    public void shouldConvert4() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab.cd.f.|", converter.handle("ab|cdf"));
    }

    @Test
    public void shouldConvert5() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab.cd.f.|dd.|", converter.handle("ab|cdf|dd"));
    }

    @Test
    public void shouldConvert6() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab.c.dd.|", converter.handle("abc|dd"));
    }

    @Test
    public void shouldConvert7() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab.cdd.|.", converter.handle("ab(c|dd)"));
    }

    @Test
    public void shouldConvert8() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab*.", converter.handle("ab*"));
    }

    @Test
    public void shouldConvert9() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab.*", converter.handle("(ab)*"));
    }

    @Test
    public void shouldConvert10() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab|*", converter.handle("(a|b)*"));
    }

    @Test
    public void shouldConvert11() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
        assertEquals("ab|*a.", converter.handle("(a|b)*a"));
    }

    @Test
    public void shouldConvertAndPrint() {
        InfixPostfixConverter converter = new InfixPostfixConverter();
      //  System.out.println(converter.handle("abb(c|dc*)bb"));
        System.out.println(converter.handle("(a|b)*abb#"));
       // System.out.println(converter.makeStringInfixed("(a|b)*a"));
    }
}
