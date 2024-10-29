package org.racketsimulator.expression;

import org.racketsimulator.thowables.RacketSimError;

public class InvalidExpression extends RacketSimError {
    public InvalidExpression(String message) {
        super(message);
    }
}
