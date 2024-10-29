package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.SExpression;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;
import java.util.Objects;

public class Cond extends BuiltinCallable {
    private final ExpressionBuilder builder;

    public Cond(Environment runtime, ExpressionBuilder builder) {
        super(runtime);
        this.builder = builder;
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.isEmpty()) {
            throw new InvalidCallableArgs("Operation cond requires at least one condition");
        }

        for (Expression arg : args) {
            if (arg instanceof SExpression && arg.valueSize() >= 2) {
                List<Expression> conditions = resolveConditions(arg);

                String conditionContent = conditions.getFirst().stringContent();
                if ((Objects.equals(conditionContent, "else") && arg != args.getLast()))
                    throw new InvalidCallableArgs("Else condition can only be used once in the end of cond.");

                if (Objects.equals(conditionContent, "#t") ||
                        (Objects.equals(conditionContent, "else") && arg == args.getLast())) {
                    return conditions.getLast().evaluate();
                }
            }
        }

        throw new InvalidCallableArgs("All of the conditions are false and there is no else condition.");
    }

    private List<Expression> resolveConditions(Expression arg) {
        String content = arg.stringContent().trim();
        int limit = content.indexOf(' ');

        Expression result = builder.build(arg.content().getFirst().stringContent());
        Expression executable = builder.build(arg.content().getLast().stringContent());
        return List.of(result.evaluate(), executable);
    }

}
