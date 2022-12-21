package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;

public class Ascending extends ComparisonInfix {
    public Ascending() {}
    public Ascending(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "greater";
    }
}
