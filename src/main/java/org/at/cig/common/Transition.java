package org.at.cig.common;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/24/15
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transition {
    private final String symbol;

    public Transition() {
        symbol = null;
    }

    public Transition(String symbol) {
        this.symbol = symbol;
    }

     public boolean isEmty() {
         return symbol == null;
     }

    public String getSymbol() {
        return symbol;
    }
}
