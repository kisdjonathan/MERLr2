package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Nor extends BinaryOperator {
    public Nor() {}
    public Nor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("nor");
        setEvaluation("nor", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(!(x.getValue() || y.getValue())));
        setEvaluation("nor", new Int(), new Int(), new Int(), (x, y) -> new Int(~(x.getValue() | y.getValue())));
    }

    public Nor clone() {
        return new Nor(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "nor";
    }
}
