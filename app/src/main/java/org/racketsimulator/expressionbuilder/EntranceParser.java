package org.racketsimulator.expressionbuilder;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.DefinedCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.*;
import java.util.stream.Collectors;

public class EntranceParser extends GeneralParser {
    private static final Character SUGAR = '\'';
    private static final String SUGAR_REPLACEMENT = "(quote ";
    private final Environment runtime;

    public EntranceParser(Environment runtime) {
        super(runtime);
        this.runtime = runtime;
    }

    /**
     * @param sentence The call to be parsed into an Expression.
     * @return a prepared Expression based on the arguments.
     */
    @Override
    public Expression build(final String sentence) {
        String cleaned = clearSugar(sentence);
        List<String> tokens = tokenise(cleaned);
        return parse(tokens);
    }

    private String clearSugar(String sentence) {
        StringBuilder result = new StringBuilder();
        int openParensCount = 0;
        boolean insideQuote = false;

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);

            if (ch == SUGAR && i + 1 < sentence.length()) {
                if (sentence.charAt(i + 1) == OPEN.charAt(0)) {
                    result.append(SUGAR_REPLACEMENT);
                    insideQuote = true;
                    openParensCount = 1;
                    i++;
                } else if (Character.isLetterOrDigit(sentence.charAt(i + 1))) {
                    result.append(SUGAR_REPLACEMENT);
                    int j = i + 1;
                    while (j < sentence.length() && Character.isLetterOrDigit(sentence.charAt(j))) {
                        result.append(sentence.charAt(j));
                        j++;
                    }
                    result.append(CLOSE);
                    i = j - 1;
                }
            } else {
                result.append(ch);

                if (insideQuote) {
                    if (ch == OPEN.charAt(0)) {
                        openParensCount++;
                    } else if (ch == CLOSE.charAt(0)) {
                        openParensCount--;
                    }

                    if (openParensCount == 0) {
                        result.append(CLOSE);
                        insideQuote = false;
                    }
                }
            }
        }

        return result.toString();
    }

    private Expression parse(List<String> tokens) {
        Iterator<String> iterator = tokens.iterator();
        return readFromTokens(iterator);
    }

    private Expression readFromTokens(Iterator<String> iterator) {
        List<Expression> expressions = new ArrayList<>();
        while (iterator.hasNext()) {
            String token = iterator.next();
            switch (token) {
                case OPEN:
                    expressions.add(readFromTokens(iterator));
                    break;

                case CLOSE:
                    if (expressions.isEmpty())
                        return new Empty();
                    return new SExpression(expressions, runtime);

                default:
                    expressions.add(evalToken(token));
                    break;
            }
        }
        if (expressions.isEmpty())
            return new Empty();
        return expressions.getFirst();
    }
}
