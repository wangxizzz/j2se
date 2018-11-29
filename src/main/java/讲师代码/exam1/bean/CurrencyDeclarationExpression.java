package 讲师代码.exam1.bean;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 */
public final class CurrencyDeclarationExpression extends CurrencyExpression {
    private final Money money;

    CurrencyDeclarationExpression(Money money) {
        super(Type.DECLARATION);
        this.money = money;
    }

    @Override
    public CurrencyDeclarationExpression castToDeclaration() {
        return this;
    }

    public Money getMoney() {
        return money;
    }
}
