package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class BitOr extends BitwiseOperator {
    public BitOr() {}
    public BitOr(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "bit or";
    }
}
