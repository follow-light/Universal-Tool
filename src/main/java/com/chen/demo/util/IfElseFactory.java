package com.chen.demo.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author chenzhuang
 * @create_time 2022/3/9 14:32:56
 * @description
 */
public class IfElseFactory {
    private Supplier<Boolean> ifExpression;

    private Supplier<String> elseExpression;

    private IfElseFactory(Supplier<Boolean> ifExpression) {
        this.ifExpression = ifExpression;
    }

    public static IfElseFactory firstJudge(Supplier<Boolean> ifExpression) {
        return new IfElseFactory(ifExpression);
    }

    public IfElseFactory ifExpression(Supplier<Boolean> ifExpression) {
        this.ifExpression = this.ifExpression.get() ? ifExpression : this.ifExpression;
        return this;
    }

    public IfElseFactory reversion(Supplier<String> elseExpression) {
        this.elseExpression = !this.ifExpression.get() && this.elseExpression == null ?elseExpression : this.elseExpression;
        return this;
    }

    public IfElseFactory accept(Consumer<String> consumer) {
        if (elseExpression == null) {
            consumer.accept(elseExpression.get());
        }
        return this;
    }

    public Boolean get() {
        return this.ifExpression.get();
    }
}
