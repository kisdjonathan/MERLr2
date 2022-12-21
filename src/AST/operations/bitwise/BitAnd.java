package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class BitAnd extends BitwiseInfix{
    public BitAnd() {}
    public BitAnd(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "bit and";
    }
}
