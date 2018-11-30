package 讲师代码.exam1.bean;

import java.util.Objects;

/**
 *
 * @author Mingxin Wang
 */
public final class CurrencyUnit {
    final String symbol;
    final boolean left;

    CurrencyUnit(String symbol, boolean left) {
        this.symbol = symbol;
        this.left = left;
    }

    @Override
    public String toString() {
        return left ? symbol + "?" : "? " + symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyUnit that = (CurrencyUnit) o;
        return left == that.left &&
                Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, left);
    }
}
