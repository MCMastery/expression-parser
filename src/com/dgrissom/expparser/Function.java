package com.dgrissom.expparser;

public enum Function implements IFunction {
    /*
    Represents the "sin" function
     */
    SINE {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return new ValueToken(Math.sin(args[0].getValue()));
        }

        @Override
        public String getToken() {
            return "sin";
        }

        @Override
        public int getArgs() {
            return 1;
        }
    };

    /*
    Returns the function with the given token. If no function exists with that token, it returns null
     */
    public static Function fromToken(String token) {
        for (Function function : values())
            if (function.getToken().equals(token))
                return function;
        return null;
    }
}
