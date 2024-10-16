package org.racketsimulator.environment;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.expression.Symbol;

import java.util.Optional;

public interface Environment {
    void defineSymbol(Symbol symbol, Callable callable);
    Optional<Callable> search(Symbol symbol);
}
