package com.dgrissom.expparser;

public interface IOperator extends IFunction {
    enum Associativity {
        LEFT, RIGHT
    }

    int getPrecedence();
    Associativity getAssociativity();

    /*
    All operators have 2 arguments (except for factorial, then you could override this)
     */
    default int getArgs() {
        return 2;
    }
}
