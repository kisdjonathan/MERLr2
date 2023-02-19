package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.baseTypes.numerical.Numerical;
import AST.operations.BinaryOperator;

public class NoEqual extends BinaryOperator {
    public static BasicType noEqual(BasicType node1, BasicType node2) {
        return interpretEvaluate("not equal", node1.interpret(), node2.interpret());
    }

    public NoEqual(){}
    public NoEqual(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    static {
        addEvaluationOperation("not equal");
        setEvaluation("not equal", new Int(), new Int(), new Bool(), (x, y) -> new Bool(x.asInt() != y.asInt()));
        setEvaluation("not equal", new Float(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() != y.asDouble()));
        setEvaluation("not equal", new Float(), new Int(), new Bool(), (x, y) -> new Bool(x.asDouble() != y.asDouble()));
        setEvaluation("not equal", new Int(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() != y.asDouble()));
        setEvaluation("not equal", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() != y.getValue()));
    }
    
    public SyntaxNode clone() {
        return new NoEqual(getChild(0),getChild(1));
    }

    public String getName() {
        return "not equal";
    }

}
