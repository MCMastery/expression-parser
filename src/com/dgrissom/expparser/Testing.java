package com.dgrissom.expparser;

public class Testing {
    public static void main(String[] args) {
        String exp = "sin(3+4*2/(1-5)^2^3)";
        ExpressionParser parser = new ExpressionParser(exp);
        System.out.println(parser.evaluate());
    }
}
