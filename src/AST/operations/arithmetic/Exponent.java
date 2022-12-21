package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;

public class Exponent extends ArithmeticInfix{
    public Exponent(){}
    public Exponent(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "exp";
    }
}
