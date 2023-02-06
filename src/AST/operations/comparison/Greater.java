package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.baseTypes.numerical.Numerical;
import AST.operations.BinaryOperator;

public class Greater extends BinaryOperator {
    public Greater(){}
    public Greater(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    static {
        addEvaluationOperation("greater");
        setEvaluation("greater", new Int(), new Int(), new Bool(), (x, y) -> new Bool(x.asInt() > y.asInt()));
        setEvaluation("greater", new Float(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() > y.asDouble()));
        setEvaluation("greater", new Float(), new Int(), new Bool(), (x, y) -> new Bool(x.asDouble() > y.asDouble()));
        setEvaluation("greater", new Int(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() > y.asDouble()));
    }

    public SyntaxNode clone() {
        return new Greater(getChild(0),getChild(1));
    }

    public String getName() {
        return "greater";
    }

}
