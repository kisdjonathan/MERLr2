package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class BitXnor extends BitwiseInfix{
    public BitXnor() {}
    public BitXnor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "bit xnor";
    }
}
