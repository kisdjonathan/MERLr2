package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;

public class Subtract extends ArithmeticInfix {
    public Subtract(){}
    public Subtract(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "sub";
    }
}
