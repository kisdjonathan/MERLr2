package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;
import AST.baseTypes.Int;

public class Xnor extends BitwiseOperator {
    public Xnor() {}
    public Xnor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        return new Bool(first.getValue() == second.getValue());
    }

    @Override
    protected Int interpretBytes(Int first, Int second) {
        return new Int(~(first.asInt() ^ second.asInt()));
    }

    public String getName() {
        return "xnor";
    }
}
