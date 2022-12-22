package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;

public class Not extends BitwiseOperator {
    public Not(){}

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        return new Bool(!first.getValue());
    }

    @Override
    protected Char interpretBytes(Char first, Char second) {
        return new Char((short) ~first.getValue());
    }

    public Not(SyntaxNode value) {
        super(value, null);
    }

    public String getName() {
        return "not";
    }
}
