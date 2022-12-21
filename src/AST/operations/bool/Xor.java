package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class Xor extends BoolOperator {
    public Xor() {}
    public Xor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "xor";
    }
}
