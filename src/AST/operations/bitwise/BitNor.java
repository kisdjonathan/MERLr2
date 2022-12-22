package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class BitNor extends BitwiseOperator {
    public BitNor() {}
    public BitNor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "bit nor";
    }
}
