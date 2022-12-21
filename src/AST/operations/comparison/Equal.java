package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;

public class Equal extends ComparisonInfix {
    public Equal() {}
    public Equal(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "equal";
    }
}
