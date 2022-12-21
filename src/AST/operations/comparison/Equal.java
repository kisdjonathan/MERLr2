package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;

public class Equal extends ComparisonOperator {
    public Equal() {}
    public Equal(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "equal";
    }
}
