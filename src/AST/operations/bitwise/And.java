package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.Float;
import AST.baseTypes.Str;

public class And extends BitwiseOperator {
    public And() {}
    public And(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    @Override
    protected void setEvaluations() {
        setEvaluation(new Bool(), new Bool(), new Bool(), (x , y) -> new Bool(x.getValue() && y.getValue()));
        setEvaluation(new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() & y.getValue()));
    }

    public And clone() {
        return new And(getChild(0).clone(), getChild(1).clone());
    }

    @Override
    protected Bool interpretBools(Bool first, Bool second) {
        return new Bool(first.getValue() && second.getValue());
    }

    @Override
    protected Int interpretBytes(Int first, Int second) {
        return new Int(first.asInt() & second.asInt());
    }

    protected Str interpretStrings(Str first, Str second) {
        return new Str(first.getValue() + second.getValue());
    }

    public String getName() {
        return "and";
    }
}
