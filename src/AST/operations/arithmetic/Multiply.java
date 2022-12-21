package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;

public class Multiply extends ArithmeticInfix{
    public Multiply(){}
    public Multiply(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "mul";
    }
}
