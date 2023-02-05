package AST.operations.arithmetic;

import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.abstractNode.SyntaxNode;
import AST.operations.UnaryOperator;

public class Negative extends UnaryOperator {
    public Negative(){}
    public Negative(SyntaxNode value) {
        addChild(value);
    }

    static {
        setEvaluation(new Int(), new Int(), x -> new Int(-x.asInt()));
        setEvaluation(new Float(), new Float(), x -> new Float(x.asDouble()));
    }

    public String getName() {
        return "negative";
    }

    public Negative clone() {
        return new Negative(getChild(0).clone());
    }

}
