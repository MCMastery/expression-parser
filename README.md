## expresion-parser
#### Usage
You can add any operators / functions you like, by editing the Function and Operator enums. (I will add dynamic way soon).

Usage is very simple. Here is an example of parsing the expression "sin(3+4*2/(1-5)^2^3)":

        String exp = "sin(3+4*2/(1-5)^2^3)";
        ExpressionParser parser = new ExpressionParser(exp);
        double value = parser.evaluate();

That's it for now.

##Todo
* Add variables
* Dynamically add new operators and functions
* Allow expressions like "2(3+4)" to be parsed as multiplication