package 讲师代码.exam1.bean;

import com.google.common.collect.ImmutableSet;
import 讲师代码.exam1.exception.IllFormedExpressionException;

/**
 *
 * @author Mingxin Wang
 */
public abstract class CurrencyExpression {
    private static final char QUESTION_MARK = '?';
    private static final ImmutableSet<Character> NUMBER_DIGITS
            = ImmutableSet.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.');
    private static final ExpressionParser[] EXPRESSION_PARSERS = { new ExpressionParser() {
        @Override
        public boolean match(String s) {
            return s.charAt(0) == QUESTION_MARK;
        }

        @Override
        public CurrencyExpression parse(String s) throws IllFormedExpressionException {
            return new CurrencyRequestExpression(parseUnit(s, 1, false));
        }
    }, new ExpressionParser() {
        @Override
        public boolean match(String s) {
            return s.charAt(s.length() - 1) == QUESTION_MARK;
        }

        @Override
        public CurrencyExpression parse(String s) throws IllFormedExpressionException {
            return new CurrencyRequestExpression(parseUnit(s, s.length() - 1, true));
        }
    }, new ExpressionParser() {
        @Override
        public boolean match(String s) {
            return NUMBER_DIGITS.contains(s.charAt(0));
        }

        @Override
        public CurrencyExpression parse(String s) throws IllFormedExpressionException {
            for (int i = 1; i < s.length(); ++i) {
                if (!NUMBER_DIGITS.contains(s.charAt(i))) {
                    return new CurrencyDeclarationExpression(new Money(parseAmount(s, 0, i), parseUnit(s, i, false)));
                }
            }
            throw new IllegalArgumentException(s);
        }
    }, new ExpressionParser() {
        @Override
        public boolean match(String s) {
            return NUMBER_DIGITS.contains(s.charAt(s.length() - 1));
        }

        @Override
        public CurrencyExpression parse(String s) throws IllFormedExpressionException {
            for (int i = s.length() - 2; i >= 0; --i) {
                if (!NUMBER_DIGITS.contains(s.charAt(i))) {
                    return new CurrencyDeclarationExpression(new Money(parseAmount(s, i + 1, s.length()), parseUnit(s, i + 1, true)));
                }
            }
            throw new IllegalArgumentException(s);
        }
    }};

    private final Type type;

    public enum Type {
        REQUEST, DECLARATION
    }

    public static CurrencyExpression parse(String s) throws IllFormedExpressionException {
        s = s.trim();
        if (s.isEmpty()) {
            throw new IllFormedExpressionException("Expression '" + s + "' is empty");
        }
        ExpressionParser parser = null;
        for (ExpressionParser currentParser : EXPRESSION_PARSERS) {
            if (currentParser.match(s)) {
                if (parser != null) {
                    throw new IllFormedExpressionException("Expression '" + s + "' is ambiguous");
                }
                parser = currentParser;
            }
        }
        if (parser == null) {
            throw new IllFormedExpressionException("Expression '" + s + "' is ill-formed");
        }
        return parser.parse(s);
    }

    public final Type getType() {
        return type;
    }

    public CurrencyDeclarationExpression castToDeclaration() {
        throw new UnsupportedOperationException();
    }

    public CurrencyRequestExpression castToRequest() {
        throw new UnsupportedOperationException();
    }

    CurrencyExpression(Type type) {
        this.type = type;
    }

    private interface ExpressionParser {
        boolean match(String s);

        CurrencyExpression parse(String s) throws IllFormedExpressionException;
    }

    private static CurrencyUnit parseUnit(String s, int index, boolean left) throws IllFormedExpressionException {
        String result = (left ? s.substring(0, index) : s.substring(index)).trim();
        if (result.isEmpty()) {
            throw new IllFormedExpressionException("Currency unit shall not be empty");
        }
        for (char c : result.toCharArray()) {
            if (c == QUESTION_MARK || c == ' ') {
                throw new IllFormedExpressionException("Currency unit shall not contain question mark or white space");
            }
        }
        return new CurrencyUnit(result, left);
    }

    private static double parseAmount(String s, int from, int to) throws IllFormedExpressionException {
        try {
            return Double.parseDouble(s.substring(from, to));
        } catch (NumberFormatException e) {
            throw new IllFormedExpressionException("Parse money quantity '" + s + "' failed.", e);
        }
    }
}
