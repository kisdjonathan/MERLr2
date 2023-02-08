package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class Or extends Operator {
    public Or() {}
    public Or(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("or");
        setRawBinaryEvaluation("or", new Bool(), new Bool(), new Bool(), (x, y) -> {
            if (((Bool) x.interpret()).getValue()) {
                return new Bool(true);
            } else {
                return new Bool(((Bool) x.interpret()).getValue() || ((Bool) y.interpret()).getValue());
            }
        });
        setBinaryEvaluation("or", new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() | y.getValue()));
    }

    public Or clone() {
        return new Or(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "or";
    }
}
