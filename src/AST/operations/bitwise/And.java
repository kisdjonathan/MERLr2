package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class And extends BinaryOperator {
    public And() {}
    public And(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("and");
        setRawEvaluation("and", new Bool(), new Bool(), new Bool(), (x, y) -> {
            if (!((Bool) x.interpret()).getValue()){
                return new Bool(false);
            } else {
                return new Bool(((Bool) x.interpret()).getValue() && ((Bool) y.interpret()).getValue());
            }
        });
        setEvaluation("and", new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() & y.getValue()));
    }

    public And clone() {
        return new And(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "and";
    }
}
