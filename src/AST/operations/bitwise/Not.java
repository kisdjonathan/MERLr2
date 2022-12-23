package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;
import AST.baseTypes.Int;

public class Not extends BitwiseOperator {
    public Not(){}

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        return new Bool(!first.getValue());
    }

    @Override
    protected Int interpretBytes(Int first, Int second) {
        return new Int(~first.asInt());
    }

    public Not(SyntaxNode value) {
        super(value, null);
    }

    public String getName() {
        return "not";
    }
}
