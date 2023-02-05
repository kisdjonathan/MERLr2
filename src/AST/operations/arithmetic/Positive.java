package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.operations.UnaryOperator;

public class Positive extends UnaryOperator {
    public Positive(){}
    public Positive(SyntaxNode value) {
        addChild(value);
    }

    static {
        setEvaluation(new Int(), new Int(), x -> x);
        setEvaluation(new Float(), new Float(), x -> x);
    }

    public String getName() {
        return "positive";
    }

    public Positive clone() {
        return new Positive(getChild(0).clone());
    }

}
