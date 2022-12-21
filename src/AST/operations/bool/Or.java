package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class Or extends BoolOperator {
    public Or() {}
    public Or(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "or";
    }
}
