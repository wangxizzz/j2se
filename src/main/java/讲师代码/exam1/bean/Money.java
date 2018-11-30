package 讲师代码.exam1.bean;

/**
 *
 * @author Mingxin Wang
 */
public final class Money {
    private final double amount;
    private final CurrencyUnit unit;

    public Money(double amount, CurrencyUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public CurrencyUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return unit.left ? String.format("%s%.4f", unit.symbol, amount)
                : String.format("%.4f %s", amount, unit.symbol);
    }
}
