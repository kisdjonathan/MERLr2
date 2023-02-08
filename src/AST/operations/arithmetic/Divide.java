package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class Divide extends Operator {
    public Divide(){}
    public Divide(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("div");
        setBinaryEvaluation("div", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() / y.asInt()));
        setBinaryEvaluation("div", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setBinaryEvaluation("div", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setBinaryEvaluation("div", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
    }

    public String getName() {
        return "div";
    }

    public Divide clone() {
        return new Divide(getChild(0).clone(), getChild(1).clone());
    }

}
