package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Int;
import AST.operations.arithmetic.Add;

public class RightShift extends BitwiseOperator {
    public RightShift() {}
    public RightShift(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    @Override
    protected void setEvaluations() {

    }

    public RightShift clone() {
        return new RightShift(getChild(0).clone(), getChild(1).clone());
    }

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        throw new Error("Unsupported arguments for right shift operator: \n\tfirst: boolean\n\tsecond: boolean");
    }

    @Override
    protected Int interpretBytes(Int first, Int second) {
        return null;
    }

    public String getName() {
        return "right shift";
    }
}
