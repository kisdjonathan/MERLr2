package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;

public class Descending extends ComparisonInfix {
    public Descending() {}
    public Descending(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "not greater";
    }
}
