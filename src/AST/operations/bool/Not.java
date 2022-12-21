package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class Not extends BoolOperator {
    public Not(){}
    public Not(SyntaxNode value) {
        super(value);
    }

    public String getName() {
        return "not";
    }
}
