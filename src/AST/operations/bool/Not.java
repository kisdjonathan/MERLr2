package AST.operations.bool;

import AST.abstractNode.SyntaxNode;

public class Not extends BooleanUnifix {
    public Not(){}
    public Not(SyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "not";
    }
}
