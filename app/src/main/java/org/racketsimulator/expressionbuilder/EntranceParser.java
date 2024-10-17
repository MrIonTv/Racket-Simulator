package org.racketsimulator.expressionbuilder;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.DefinedCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.*;
import java.util.stream.Collectors;

public class EntranceParser implements ExpressionBuilder {
    private static final String OPEN = "(";
    private static final String CLOSE = ")";
    private static final String SPACE = " ";
    private static final Character SUGAR = '\'';
    private static final String SUGAR_REPLACEMENT = "(quote ";
    private final Environment source;
    private final Environment runtime;

    public EntranceParser(Environment runtime, Environment source) {
        this.source = source;
        this.runtime = runtime;
    }

    /**
     * @param sentence 
     * @return
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

    private List<String> tokenise(String cleaned) {
        return Arrays.stream(
                cleaned.replace(OPEN, SPACE + OPEN + SPACE)
                .replace(CLOSE, SPACE + CLOSE + SPACE)
                .split("\\s+"))
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
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
                    return new SExpression(expressions, source, runtime);

                default:
                    expressions.add(evalToken(token));
                    break;
            }
        }
        if (expressions.isEmpty())
            return new Empty();
        return expressions.getFirst();
    }

    private Expression evalToken(String token) {
        if (token.isEmpty())
            return new Empty();
        if (token.matches("-?\\d+"))
            return new Numeric(Integer.parseInt(token));
        return evalSymbol(new Symbol(token));
    }

    private Expression evalSymbol(Symbol token) {
        Optional<Callable> value = runtime.search(token);
        if (value.isEmpty())
            return token;
        return new Symbol(value.get().execute(Optional.empty()).content());
    }
}
