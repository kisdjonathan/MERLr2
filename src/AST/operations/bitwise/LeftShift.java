package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class LeftShift extends  BitwiseInfix{
    public LeftShift() {}
    public LeftShift(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "left shift";
    }
}
