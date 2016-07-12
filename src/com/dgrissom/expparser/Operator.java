package com.dgrissom.expparser;

/*
Contains the operators that are available during expression parsing
 */
public enum Operator implements IOperator {
    /*
    Used by parser internally
     */
    ARGUMENT_SEPARATOR {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return null;
        }

        @Override
        public String getToken() {
            return ",";
        }

        @Override
        public int getPrecedence() {
            return 0;
        }

        @Override
        public Associativity getAssociativity() {
            return null;
        }
    },


    /*
    Used by parser internally
     */
    LEFT_PARENTHESIS {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return null;
        }

        @Override
        public String getToken() {
            return "(";
        }

        @Override
        public int getPrecedence() {
            return 0;
        }

        @Override
        public Associativity getAssociativity() {
            return null;
        }
    },


    /*
    Used by parser internally
     */
    RIGHT_PARENTHESIS {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return null;
        }

        @Override
        public String getToken() {
            return ")";
        }

        @Override
        public int getPrecedence() {
            return 0;
        }

        @Override
        public Associativity getAssociativity() {
            return null;
        }
    },


    /*
    Represents the "+" operator
     */
    ADDITION {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return new ValueToken(args[0].getValue() + args[1].getValue());
        }

        @Override
        public String getToken() {
            return "+";
        }

        @Override
        public int getPrecedence() {
            return 2;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.LEFT;
        }
    },


    /*
    Represents the "-" operator
     */
    SUBTRACTION {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return new ValueToken(args[0].getValue() - args[1].getValue());
        }

        @Override
        public String getToken() {
            return "-";
        }

        @Override
        public int getPrecedence() {
            return 2;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.LEFT;
        }
    },


    /*
    Represents the "*" operator
     */
    MULTIPLICATION {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return new ValueToken(args[0].getValue() * args[1].getValue());
        }

        @Override
        public String getToken() {
            return "*";
        }

        @Override
        public int getPrecedence() {
            return 3;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.LEFT;
        }
    },


    /*
    Represents the "/" operator
     */
    DIVISION {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return new ValueToken(args[0].getValue() / args[1].getValue());
        }

        @Override
        public String getToken() {
            return "/";
        }

        @Override
        public int getPrecedence() {
            return 3;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.LEFT;
        }
    },


    /*
    Represents the "^" operator
     */
    EXPONENTIATION {
        @Override
        public ValueToken evaluate(ValueToken... args) {
            return new ValueToken(Math.pow(args[0].getValue(), args[1].getValue()));
        }

        @Override
        public String getToken() {
            return "^";
        }

        @Override
        public int getPrecedence() {
            return 4;
        }

        @Override
        public Associativity getAssociativity() {
            return Associativity.RIGHT;
        }
    };

    /*
    Returns the operator with the given token. If no operator exists with that token, it returns null
     */
    public static Operator fromToken(String token) {
        for (Operator operator : values())
            if (operator.getToken().equals(token))
                return operator;
        return null;
    }
}
