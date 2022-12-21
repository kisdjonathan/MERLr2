package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class Nor extends BoolOperator {
    public Nor() {}
    public Nor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "nor";
    }
}
