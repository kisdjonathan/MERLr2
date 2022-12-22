package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;

public class RightShift extends BitwiseOperator {
    public RightShift() {}
    public RightShift(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        throw new Error("Unsupported arguments for right shift operator: \n\tfirst: boolean\n\tsecond: boolean");
    }

    @Override
    protected Char interpretBytes(Char first, Char second) {
        return null;
    }

    public String getName() {
        return "right shift";
    }
}
