package com.dgrissom.expparser;

public interface IToken {
    String getToken();

    /*
    Returns an IToken from the given string
     */
    static IToken parse(String token) {
        // check if it is a value
        ValueToken valueToken = ValueToken.parse(token);
        if (valueToken != null)
            return valueToken;

        // check if it is an operator
        Operator operator = Operator.fromToken(token);
        if (operator != null)
            return operator;


        // check if it is a function
        Function function = Function.fromToken(token);
        if (function != null)
            return function;

        // must be an in valid token
        return null;
    }
}
