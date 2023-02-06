package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Numerical;
import AST.operations.BinaryOperator;

public class Equal extends BinaryOperator {
    public Equal(){}
    public Equal(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    static {
        addEvaluationOperation("equal");
        setEvaluation("equal", new Int(), new Int(), new Bool(), (x, y) -> new Bool(x.asInt() == y.asInt()));
        setEvaluation("equal", new Float(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() == y.asDouble()));
        setEvaluation("equal", new Float(), new Int(), new Bool(), (x, y) -> new Bool(x.asDouble() == y.asDouble()));
        setEvaluation("equal", new Int(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() == y.asDouble()));
        setEvaluation("equal", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() == y.getValue()));
    }

    public SyntaxNode clone() {
        return new Equal(getChild(0), getChild(1));
    }

    public String getName() {
        return "equal";
    }

}
