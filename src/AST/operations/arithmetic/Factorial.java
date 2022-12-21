package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;

public class Factorial extends ArithmeticInfix {
    public Factorial(){}
    public Factorial(SyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "factorial";
    }
}
