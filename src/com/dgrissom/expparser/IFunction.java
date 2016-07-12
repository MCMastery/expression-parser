package com.dgrissom.expparser;

public interface IFunction extends IToken {
    ValueToken evaluate(ValueToken[] args);
    int getArgs();
}
