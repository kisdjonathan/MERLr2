package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.baseTypes.Str;
import AST.operations.BinaryOperator;
import util.Pair;

public class Divide extends BinaryOperator {
    public Divide(){}
    public Divide(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static {
        setEvaluation(new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() / y.asInt()));
        setEvaluation(new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setEvaluation(new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setEvaluation(new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
    }

    public String getName() {
        return "div";
    }

    public Divide clone() {
        return new Divide(getChild(0).clone(), getChild(1).clone());
    }

}
