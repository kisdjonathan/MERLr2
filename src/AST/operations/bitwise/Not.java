package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class Not extends Operator {
    public Not(){}

    static  {
        addEvaluationOperation("not");
        setUnaryEvaluation("not", new Bool(), new Bool(), (x) -> new Bool(!x.getValue()));
        setUnaryEvaluation("not", new Int(), new Int(), (x) -> new Int(~x.getValue()));
    }

    public Not(SyntaxNode value) {
        addChild(value);
    }

    public Not clone() {
        return new Not(getChild(0).clone());
    }

    public String getName() {
        return "not";
    }
}
