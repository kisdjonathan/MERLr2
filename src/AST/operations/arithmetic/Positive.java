package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.UnaryOperator;

public class Positive extends UnaryOperator {
    public Positive(){}
    public Positive(SyntaxNode value) {
        addChild(value);
    }

    static {
        addEvaluationOperation("positive");
        setEvaluation("positive", new Int(), new Int(), x -> x);
        setEvaluation("positive", new Float(), new Float(), x -> x);
    }

    public String getName() {
        return "positive";
    }

    public Positive clone() {
        return new Positive(getChild(0).clone());
    }

}
