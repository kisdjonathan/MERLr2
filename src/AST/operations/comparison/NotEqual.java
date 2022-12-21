package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;

public class NotEqual extends ComparisonInfix {
    public NotEqual() {}
    public NotEqual(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "not equal";
    }
}
