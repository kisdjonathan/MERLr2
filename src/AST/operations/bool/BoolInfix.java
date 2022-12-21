package AST.operations.bool;

import AST.operations.Operator;
import baseTypes.Bool;
import AST.abstractNode.SyntaxNode;
import operations.BuiltinOperation;

public class BoolInfix extends Operator {
    public BoolInfix() {}
    public BoolInfix(SyntaxNode a, SyntaxNode b) {
        setOrigin(a);
        setVector(b);
    }

    public SyntaxNode getDeclaredType() {
        return new Bool();
    }
}
