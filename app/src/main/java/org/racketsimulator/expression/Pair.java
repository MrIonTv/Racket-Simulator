package org.racketsimulator.expression;

import java.util.List;

public class Pair implements Expression{
    public static final String SPACE = " ";
    private static final String CLOSE = ")";
    private static final String OPEN = "'(";
    private final List<Expression> values;

    public Pair(List<Expression> values) {
        this.values = values;
    }

    @Override
    public Expression evaluate() {
        return this;
    }

    @Override
    public List<Expression> content() {
        return values;
    }

    @Override
    public int valueSize() {
        return values.size();
    }

    @Override
    public String stringContent() {
        StringBuilder result = new StringBuilder();

        result.append(OPEN);
        for (Expression expression : values.subList(0, values.size() - 1)) {
            result.append(expression.stringContent());
            result.append(SPACE);
        }

        result.append(values.getLast().stringContent());
        result.append(CLOSE);

        return result.toString();
    }
}
