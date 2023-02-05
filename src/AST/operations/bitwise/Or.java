package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;
import AST.baseTypes.Int;
import AST.operations.BinaryOperator;
import AST.operations.arithmetic.Add;

public class Or extends BinaryOperator {
    public Or() {}
    public Or(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("or");
        setEvaluation("or", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() || y.getValue()));
        setEvaluation("or", new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() | y.getValue()));
    }

    public Or clone() {
        return new Or(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "or";
    }
}
