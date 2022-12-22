package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;

public class Or extends BitwiseOperator {
    public Or() {}
    public Or(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        return new Bool(first.getValue() || second.getValue());
    }

    @Override
    protected Char interpretBytes(Char first, Char second) {
        return new Char((short) (first.getValue() | second.getValue()));
    }

    public String getName() {
        return "or";
    }
}
