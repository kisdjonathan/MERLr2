package AST.operations.arithmetic;

import AST.operations.Operator;
import baseTypes.BasicType;
import AST.abstractNode.SyntaxNode;
import operations.BuiltinOperation;

public class ArithmeticInfix extends Operator {
    private static SyntaxNode largerType(SyntaxNode a, SyntaxNode b) {
        BasicType t1 = a.getBaseType(), t2 = b.getBaseType();
        return t1.getByteSize().compareTo(t2.getByteSize()) > 0 ? a : b;
    }

    public ArithmeticInfix() {}
    public ArithmeticInfix(SyntaxNode a, SyntaxNode b) {
        setOrigin(a);
        setVector(b);
    }

    public SyntaxNode getDeclaredType() {
        return largerType(getOrigin(), getVector());
    }
}
