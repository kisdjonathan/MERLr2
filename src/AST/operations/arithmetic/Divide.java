package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;

public class Divide extends ArithmeticInfix {
    public Divide(){}
    public Divide(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "div";
    }
}
