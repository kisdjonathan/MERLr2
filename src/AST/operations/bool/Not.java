package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class Not extends BoolInfix {
    public Not(){}
    public Not(SyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "not";
    }
}
