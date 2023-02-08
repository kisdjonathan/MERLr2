package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class And extends Operator {
    public And() {}
    public And(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("and");
        setRawBinaryEvaluation("and", new Bool(), new Bool(), new Bool(), (x, y) -> {
            if (!((Bool) x.interpret()).getValue()){
                return new Bool(false);
            } else {
                return new Bool(((Bool) x.interpret()).getValue() && ((Bool) y.interpret()).getValue());
            }
        });
        setBinaryEvaluation("and", new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() & y.getValue()));
    }

    public And clone() {
        return new And(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "and";
    }
}
