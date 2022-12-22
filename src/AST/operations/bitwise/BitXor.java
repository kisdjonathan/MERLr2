package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class BitXor extends BitwiseOperator {
    public BitXor() {}
    public BitXor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "bit xor";
    }
}
