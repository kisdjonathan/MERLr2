package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class Xnor extends BoolOperator {
    public Xnor() {}
    public Xnor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "xnor";
    }
}
