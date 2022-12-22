package AST.operations.bool;

import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

public abstract class BoolOperator extends Operator {
    public BoolOperator() {}
    public BoolOperator(SyntaxNode a) {
        addChild(a);
    }
    public BoolOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a); addChild(b);
    }

    public SyntaxNode getDeclaredType() {
        return new Bool();
    }

    //TODO
    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
