package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.operations.Operator;

public abstract class BitwiseOperator extends Operator {
    public BitwiseOperator() {}
    public BitwiseOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    //TODO
    public BasicType getType() {
        return null;
    }

    public BasicType interpret() {
        return null;
    }
}
