package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Subtract extends BinaryOperator {
    public static BasicType subtract(BasicType node1, BasicType node2) {
        return interpretEvaluate("sub", node1.interpret(), node2.interpret());
    }

    public Subtract(){}
    public Subtract(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("sub");
        setEvaluation("sub", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() - y.asInt()));
        setEvaluation("sub", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
        setEvaluation("sub", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
        setEvaluation("sub", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() - y.asDouble()));
    }

    public String getName() {
        return "sub";
    }

    public Subtract clone() {
        return new Subtract(getChild(0).clone(), getChild(1).clone());
    }
}
