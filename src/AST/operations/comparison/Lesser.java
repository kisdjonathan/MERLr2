package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.baseTypes.numerical.Numerical;
import AST.operations.BinaryOperator;

public class Lesser extends BinaryOperator {
    public static BasicType lesser(BasicType node1, BasicType node2) {
        return interpretEvaluate("lesser", node1.interpret(), node2.interpret());
    }

    public Lesser(){}
    public Lesser(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }
    
    static {
        addEvaluationOperation("lesser");
        setEvaluation("lesser", new Int(), new Int(), new Bool(), (x, y) -> new Bool(x.asInt() < y.asInt()));
        setEvaluation("lesser", new Float(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() < y.asDouble()));
        setEvaluation("lesser", new Float(), new Int(), new Bool(), (x, y) -> new Bool(x.asDouble() < y.asDouble()));
        setEvaluation("lesser", new Int(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() < y.asDouble()));
    }

    public SyntaxNode clone() {
        return new Lesser(getChild(0),getChild(1));
    }

    public String getName() {
        return "lesser";
    }

}
