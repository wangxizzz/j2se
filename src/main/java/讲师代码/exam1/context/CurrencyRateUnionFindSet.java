package 讲师代码.exam1.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import 讲师代码.exam1.bean.CurrencyUnit;
import 讲师代码.exam1.bean.Money;
import 讲师代码.exam1.exception.InvalidRateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Mingxin Wang
 */
final class CurrencyRateUnionFindSet {
    private static final double EPS = 1e-8;

    private final HashMap<CurrencyUnit, Money> data = Maps.newHashMap();

    void addEquivalence(Iterable<Money> monies) throws InvalidRateException {
        for (Money money : monies) {
            checkForInputMoney(money);
        }
        Map<CurrencyUnit, Money> converted = Maps.newHashMap();
        Iterator<Money> iterator = monies.iterator();
        Money first = getUniversalRepresentation(iterator.next());
        converted.put(first.getUnit(), first);
        while (iterator.hasNext()) {
            Money current = getUniversalRepresentation(iterator.next());
            Money previous = converted.get(current.getUnit());
            if (previous != null) {
                if (!isEqualWithinError(previous.getAmount(), current.getAmount())) {
                    throw new InvalidRateException("Conflict rate with previous input");
                }
            } else {
                converted.put(current.getUnit(), current);
            }
        }
        ArrayList<Money> candidates = Lists.newArrayList(converted.values());
        first = candidates.get(0);
        for (int i = 1; i < candidates.size(); ++i) {
            Money candidate = candidates.get(i);
            data.put(candidate.getUnit(), new Money(first.getAmount() / candidate.getAmount(), first.getUnit()));
        }
    }

    Money convert(Money money, CurrencyUnit unit) throws InvalidRateException {
        checkForInputMoney(money);
        money = getUniversalRepresentation(money);
        Money unitEquivalent = getUniversalRepresentation(new Money(1.0, unit));
        if (!unitEquivalent.getUnit().equals(money.getUnit())) {
            throw new InvalidRateException("There is no conversion between " + money.getUnit() + " and " + unit);
        }
        return new Money(money.getAmount() / unitEquivalent.getAmount(), unit);
    }

    private static void checkForInputMoney(Money money) throws InvalidRateException {
        if (money.getAmount() < EPS) {
            throw new InvalidRateException("The amount of money shall always be positive.");
        }
    }

    private Money getUniversalRepresentation(Money money) {
        Money equivalent = data.get(money.getUnit());
        if (equivalent == null) {
            return money;
        }
        equivalent = getUniversalRepresentation(money.getUnit(), equivalent);
        return new Money(money.getAmount() * equivalent.getAmount(), equivalent.getUnit());
    }

    private Money getUniversalRepresentation(CurrencyUnit unit, Money equivalent) {
        Money parentEquivalent = data.get(equivalent.getUnit());
        if (parentEquivalent == null) {
            return equivalent;
        }
        parentEquivalent = getUniversalRepresentation(equivalent.getUnit(), parentEquivalent);
        Money result = new Money(equivalent.getAmount() * parentEquivalent.getAmount(), parentEquivalent.getUnit());
        data.put(unit, result);
        return result;
    }

    private static boolean isEqualWithinError(double a, double b) {
        return Math.abs(a - b) < EPS;
    }
}
