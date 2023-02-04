package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;
import AST.baseTypes.Int;
import AST.operations.arithmetic.Add;

public class Not extends BitwiseOperator {
    public Not(){}

    @Override
    protected void setEvaluations() {

    }

    public Not(SyntaxNode value) {
        super(value, null);
    }

    public Not clone() {
        return new Not(getChild(0).clone());
    }

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        return new Bool(!first.getValue());
    }

    @Override
    protected Int interpretBytes(Int first, Int second) {
        return new Int(~first.asInt());
    }

    public String getName() {
        return "not";
    }
}
