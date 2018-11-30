package 讲师代码.exam1.bean;

/**
 *
 * @author Mingxin Wang
 */
public final class CurrencyRequestExpression extends CurrencyExpression {
    private final CurrencyUnit unit;

    CurrencyRequestExpression(CurrencyUnit unit) {
        super(Type.REQUEST);
        this.unit = unit;
    }

    @Override
    public CurrencyRequestExpression castToRequest() {
        return this;
    }

    public CurrencyUnit getUnit() {
        return unit;
    }
}
