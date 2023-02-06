package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Exponent extends BinaryOperator {public Exponent(){}
    public Exponent(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("exp");
        setEvaluation("exp", new Int(), new Int(), new Int(), (x, y) -> new Int((int) Math.pow(x.asInt(), y.asInt())));
        setEvaluation("exp", new Float(), new Int(), new Float(), (x, y) -> new Float(Math.pow(x.asDouble(), y.asDouble())));
        setEvaluation("exp", new Int(), new Float(), new Float(), (x, y) -> new Float(Math.pow(x.asDouble(), y.asDouble())));
        setEvaluation("exp", new Float(), new Float(), new Float(), (x, y) -> new Float(Math.pow(x.asDouble(), y.asDouble())));
    }

    public String getName() {
        return "exp";
    }

    public Exponent clone() {
        return new Exponent(getChild(0).clone(), getChild(1).clone());
    }

}
