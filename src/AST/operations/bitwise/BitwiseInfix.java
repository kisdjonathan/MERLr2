package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.operations.Operator;
import operations.BuiltinOperation;

public class BitwiseInfix extends Operator {
    public BitwiseInfix() {}
    public BitwiseInfix(SyntaxNode a, SyntaxNode b) {
        setOrigin(a);
        setVector(b);
    }
}
