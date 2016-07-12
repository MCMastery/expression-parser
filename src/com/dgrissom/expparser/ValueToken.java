package com.dgrissom.expparser;

/*
Represents a value token in an expression
 */
public class ValueToken implements IToken {
    private final double value;

    public ValueToken(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    /*
    Returns the string value of this object's number value
     */
    @Override
    public String getToken() {
        return String.valueOf(this.value);
    }


    /*
    Returns the ValueToken with the given string as a value
    If given string cannot be parsed as a Double, then this returns null
     */
    public static ValueToken parse(String token) {
        try {
            return new ValueToken(Double.parseDouble(token));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
