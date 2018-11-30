package 讲师代码.exam1.context;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 讲师代码.exam1.bean.CurrencyExpression;
import 讲师代码.exam1.bean.Money;
import 讲师代码.exam1.exception.IllFormedExpressionException;
import 讲师代码.exam1.exception.InvalidRateException;

import java.util.ArrayList;

/**
 *
 * @author Mingxin Wang
 */
public final class CurrencyCalculationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyCalculationContext.class);
    private static final Splitter EQUAL_SPLITTER = Splitter.on("=").trimResults();

    private CurrencyRateUnionFindSet unionFindSet = new CurrencyRateUnionFindSet();

    public void process(String statement) {
        if (statement.isEmpty()) {
            return;
        }
        if (statement.equals("clear")) {
            unionFindSet = new CurrencyRateUnionFindSet();
            LOGGER.info("The context has been reset.");
            return;
        }
        ArrayList<CurrencyExpression> expressions = Lists.newArrayList();
        CurrencyExpression.Type type;
        try {
            for (String expression : EQUAL_SPLITTER.split(statement)) {
                expressions.add(CurrencyExpression.parse(expression));
            }
            type = getType(expressions);
        } catch (IllFormedExpressionException e) {
            LOGGER.error("The expression is ill-formed.", e);
            return;
        }
        try {
            if (type == CurrencyExpression.Type.REQUEST) {
                Money first = expressions.get(0).castToDeclaration().getMoney();
                StringBuilder result = new StringBuilder(first.toString());
                for (int i = 1; i < expressions.size(); ++i) {
                    result.append(" = ").append(unionFindSet.convert(first, expressions.get(i).castToRequest().getUnit()));
                }
                LOGGER.info("Request result: {}", result);
            } else {
                ArrayList<Money> monies = Lists.newArrayList();
                for (CurrencyExpression expression : expressions) {
                    monies.add(expression.castToDeclaration().getMoney());
                }
                unionFindSet.addEquivalence(monies);
            }
        } catch (InvalidRateException e) {
            LOGGER.error("The input rate is invalid.", e);
        }
    }

    private static CurrencyExpression.Type getType(ArrayList<CurrencyExpression> expressions) throws IllFormedExpressionException {
        if (expressions.size() < 2) {
            throw new IllFormedExpressionException("There shall at least be two equivalent expressions");
        }
        if (expressions.get(0).getType() != CurrencyExpression.Type.DECLARATION) {
            throw new IllFormedExpressionException("The first expression shall be a declaration");
        }
        CurrencyExpression.Type result = expressions.get(1).getType();
        for (int i = 2; i < expressions.size(); ++i) {
            if (expressions.get(i).getType() != result) {
                throw new IllFormedExpressionException("All the expressions except for the first one shall either be requests or declarations");
            }
        }
        return result;
    }
}
