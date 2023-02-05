package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.baseTypes.Str;
import AST.operations.BinaryOperator;

public class Exponent extends BinaryOperator {
    public Exponent(){}
    public Exponent(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static {
        setEvaluation(new Int(), new Int(), new Int(), (x, y) -> new Int((int) Math.pow(x.asInt(), y.asInt())));
        setEvaluation(new Float(), new Int(), new Float(), (x, y) -> new Float(Math.pow(x.asDouble(), y.asDouble())));
        setEvaluation(new Int(), new Float(), new Float(), (x, y) -> new Float(Math.pow(x.asDouble(), y.asDouble())));
        setEvaluation(new Float(), new Float(), new Float(), (x, y) -> new Float(Math.pow(x.asDouble(), y.asDouble())));
    }

    public String getName() {
        return "exp";
    }

    public Exponent clone() {
        return new Exponent(getChild(0).clone(), getChild(1).clone());
    }

}
