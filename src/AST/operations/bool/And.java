package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class And extends BoolInfix{
    public And() {}
    public And(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "and";
    }
}
