package com.dgrissom.expparser;

import java.util.*;

/*
Evaluates a given string expression
See https://en.wikipedia.org/wiki/Shunting-yard_algorithm and https://en.wikipedia.org/wiki/Reverse_Polish_notation
 */
public class ExpressionParser {
    // Represents the expression to be parsed, in infix notation (ex. 3+4*5)
    private final String expression;

    public ExpressionParser(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }

    /*
    Returns the value of this object's string expression
     */
    public double evaluate() {
        return evaluatePostfix(toPostfix(tokenize())).getValue();
    }

    /*
    Splits the expression up into tokens
     */
    private Queue<IToken> tokenize() {
        String exp = this.expression.replace(" ", " ");
        // used to extract functions / operators as delimiters, and values as tokens
        String functionRegex = "";
        for (Function function : Function.values())
            functionRegex += "\\" + function.getToken() + "|";
        for (Operator operator : Operator.values())
            functionRegex += "\\" + operator.getToken() + "|";
        // remove last "|"
        if (functionRegex.endsWith("|"))
            functionRegex = functionRegex.substring(0, functionRegex.length() - 1);

        // keep the delimiters as separate tokens
        functionRegex = "((?<=" + functionRegex + ")|(?=" + functionRegex + "))";

        String[] tokenStrings = exp.split(functionRegex);

        Queue<IToken> tokens = new ArrayDeque<>();
        for (String token : tokenStrings) {
            IToken iToken = IToken.parse(token);
            if (iToken == null)
                throw new IllegalArgumentException("Invalid token (" + token + ")");
            tokens.add(iToken);
        }
        return tokens;
    }


    /*
    Converts an infix expression to postfix (RPN)
     */
    private Queue<IToken> toPostfix(Queue<IToken> tokens) {
        Stack<IFunction> operatorStack = new Stack<>();
        Queue<IToken> postfix = new ArrayDeque<>();
        for (IToken token : tokens) {
            if (token instanceof ValueToken)
                postfix.add(token);
            else if (token instanceof Function)
                operatorStack.push((Function) token);
            else if (token instanceof Operator) {
                Operator o1 = (Operator) token;
                switch (o1) {
                    case ARGUMENT_SEPARATOR:
                        while (operatorStack.peek() != Operator.LEFT_PARENTHESIS) {
                            // no left parentheses were found
                            if (operatorStack.size() == 0)
                                throw new IllegalArgumentException("Mismatched parentheses");
                            postfix.add(operatorStack.pop());
                        }
                        break;
                    case LEFT_PARENTHESIS:
                        operatorStack.push(o1);
                        break;
                    case RIGHT_PARENTHESIS:
                        while (operatorStack.peek() != Operator.LEFT_PARENTHESIS) {
                            // no left parentheses were found
                            if (operatorStack.size() == 0)
                                throw new IllegalArgumentException("Mismatched parentheses");
                            postfix.add(operatorStack.pop());
                        }
                        // pop the left parenthesis at the top of the stack
                        operatorStack.pop();
                        if (operatorStack.peek() instanceof Function)
                            postfix.add(operatorStack.pop());
                        break;
                    // this is an actual operator ("+", "*", "-", etc.)
                    default:
                        Operator o2;
                        while (operatorStack.size() != 0 && operatorStack.peek() instanceof Operator) {
                            o2 = (Operator) operatorStack.peek();
                            if (o1.getAssociativity() == IOperator.Associativity.LEFT && o1.getPrecedence() <= o2.getPrecedence()
                                    || o1.getAssociativity() == IOperator.Associativity.RIGHT && o1.getPrecedence() < o2.getPrecedence())
                                postfix.add(operatorStack.pop());
                            else
                                break;
                        }
                        operatorStack.push(o1);
                        break;
                }
            }
        }
        while (operatorStack.size() != 0) {
            IFunction func = operatorStack.peek();
            if (func == Operator.LEFT_PARENTHESIS || func == Operator.RIGHT_PARENTHESIS)
                throw new IllegalArgumentException("Mismatched parentheses");
            postfix.add(operatorStack.pop());
        }
        return postfix;
    }


    /*
    Evaluates a postfix expression
     */
    private ValueToken evaluatePostfix(Queue<IToken> tokens) {
        Stack<ValueToken> valueTokens = new Stack<>();
        for (IToken token : tokens) {
            if (token instanceof ValueToken)
                valueTokens.push((ValueToken) token);
            // operator or function
            else if (token instanceof IFunction) {
                IFunction func = (IFunction) token;
                if (valueTokens.size() < func.getArgs())
                    throw new IllegalArgumentException("Insufficient arguments for function " + func.getToken());

                // get args
                List<ValueToken> args = new ArrayList<>();
                for (int i = 0; i < func.getArgs(); i++)
                    args.add(valueTokens.pop());
                // reverse args (I don't know why this must be done.)
                Collections.reverse(args);

                ValueToken[] argArray = new ValueToken[args.size()];
                for (int i = 0; i < argArray.length; i++)
                    argArray[i] = args.get(i);

                ValueToken value = func.evaluate(argArray);
                valueTokens.push(value);
            }
        }

        if (valueTokens.size() == 1)
            return valueTokens.peek();
        throw new IllegalArgumentException("Too many values in expression");
    }
}
