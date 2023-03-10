package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Or extends BinaryOperator {
    public Or() {}
    public Or(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("or");
        setRawEvaluation("or", new Bool(), new Bool(), new Bool(), (x, y) -> {
            if (((Bool) x.interpret()).getValue()) {
                return new Bool(true);
            } else {
                return new Bool(((Bool) x.interpret()).getValue() || ((Bool) y.interpret()).getValue());
            }
        });
        setEvaluation("or", new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() | y.getValue()));
    }

    public Or clone() {
        return new Or(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "or";
    }
}
