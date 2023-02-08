package AST.operations.arithmetic;

import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.abstractNode.SyntaxNode;
import AST.operations.Operator;

public class Negative extends Operator {
    public Negative(){}
    public Negative(SyntaxNode value) {
        addChild(value);
    }

    static {
        addEvaluationOperation("negative");
        setUnaryEvaluation("negative", new Int(), new Int(), x -> new Int(-x.asInt()));
        setUnaryEvaluation("negative", new Float(), new Float(), x -> new Float(x.asDouble()));
    }

    public String getName() {
        return "negative";
    }

    public Negative clone() {
        return new Negative(getChild(0).clone());
    }

}
