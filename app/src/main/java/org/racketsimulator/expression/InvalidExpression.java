package org.racketsimulator.expression;

public class InvalidExpression extends RuntimeException {
    public InvalidExpression(String message) {
        super(message);
    }
}
