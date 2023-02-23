package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Divide extends BinaryOperator {
    public static BasicType divide(BasicType node1, BasicType node2) {
        return interpretEvaluate("div", node1.interpret(), node2.interpret());
    }
    public Divide(){}
    public Divide(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("div");
        setEvaluation("div", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() / y.asInt()));
        setEvaluation("div", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setEvaluation("div", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
        setEvaluation("div", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() / y.asDouble()));
    }

    public String getName() {
        return "div";
    }

    public Divide clone() {
        return new Divide(getChild(0).clone(), getChild(1).clone());
    }

}
