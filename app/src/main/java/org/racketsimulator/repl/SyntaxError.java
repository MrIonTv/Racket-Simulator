package org.racketsimulator.repl;

import org.racketsimulator.thowables.RacketSimError;

public class SyntaxError extends RacketSimError {
    public SyntaxError(String message) {
        super(message);
    }
}
