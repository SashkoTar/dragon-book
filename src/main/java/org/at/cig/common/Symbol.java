package org.at.cig.common;

/**
 * Created by otarasenko on 6/9/15.
 */
public class Symbol<T> {
    T symbol;

    public Symbol(T symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;

        Symbol symbol1 = (Symbol) o;

        return symbol.equals(symbol1.symbol);

    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
