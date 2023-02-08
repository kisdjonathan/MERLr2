package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.UnaryOperator;

public class AbsoluteValue extends UnaryOperator {
    public AbsoluteValue(){}
    public AbsoluteValue(SyntaxNode value) {
        addChild(value);
    }

    static {
        addEvaluationOperation("abs");
        setEvaluation("abs", new Int(), new Int(), x -> new Int(Math.abs(x.asInt())));
        setEvaluation("abs", new Float(), new Float(), x -> new Float(Math.abs(x.asDouble())));
    }

    public String getName() {
        return "abs";
    }

    public AbsoluteValue clone() {
        return new AbsoluteValue(getChild(0).clone());
    }

}
