package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.*;
import AST.baseTypes.numerical.Float;
import AST.operations.BinaryOperator;

public class Equal extends BinaryOperator {
    public static BasicType equal(BasicType node1, BasicType node2) {
        return interpretEvaluate("equal", node1.interpret(), node2.interpret());
    }

    public Equal(){}
    public Equal(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    static {
        addEvaluationOperation("equal");
        setEvaluation("equal", new Char(), new Int(), new Bool(), (x, y) -> new Bool(x.asInt() == y.asInt()));
        setEvaluation("equal", new Int(), new Char(), new Bool(), (x, y) -> new Bool(x.asInt() == y.asInt()));
        setEvaluation("equal", new Char(), new Char(), new Bool(), (x, y) -> new Bool(x.asInt() == y.asInt()));
        setEvaluation("equal", new Int(), new Int(), new Bool(), (x, y) -> new Bool(x.asInt() == y.asInt()));
        setEvaluation("equal", new Float(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() == y.asDouble()));
        setEvaluation("equal", new Float(), new Int(), new Bool(), (x, y) -> new Bool(x.asDouble() == y.asDouble()));
        setEvaluation("equal", new Int(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() == y.asDouble()));
        setEvaluation("equal", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() == y.getValue()));
    }

    public SyntaxNode clone() {
        return new Equal(getChild(0), getChild(1));
    }

    public String getName() {
        return "equal";
    }

}
