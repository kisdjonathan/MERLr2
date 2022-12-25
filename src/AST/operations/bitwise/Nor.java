package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;
import AST.baseTypes.Int;
import AST.operations.arithmetic.Add;

public class Nor extends BitwiseOperator {
    public Nor() {}
    public Nor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public Nor clone() {
        return new Nor(getChild(0).clone(), getChild(1).clone());
    }

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        return new Bool(!(first.getValue() || second.getValue()));
    }

    @Override
    protected Int interpretBytes(Int first, Int second) {
        return new Int(~(first.asInt() | second.asInt()));
    }

    public String getName() {
        return "nor";
    }
}
