package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class Subtract extends Operator {
    public Subtract(){}
    public Subtract(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("sub");
        setBinaryEvaluation("sub", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() - y.asInt()));
        setBinaryEvaluation("sub", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
        setBinaryEvaluation("sub", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
        setBinaryEvaluation("sub", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
    }

    public String getName() {
        return "sub";
    }

    public Subtract clone() {
        return new Subtract(getChild(0).clone(), getChild(1).clone());
    }
}
