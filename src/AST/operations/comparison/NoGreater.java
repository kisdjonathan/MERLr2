package AST.operations.comparison;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.baseTypes.numerical.Numerical;
import AST.operations.BinaryOperator;

public class NoGreater extends BinaryOperator {
    public NoGreater(){}
    public NoGreater(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    static {
        addEvaluationOperation("not greater");
        setEvaluation("not greater", new Int(), new Int(), new Bool(), (x, y) -> new Bool(x.asInt() <= y.asInt()));
        setEvaluation("not greater", new Float(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() <= y.asDouble()));
        setEvaluation("not greater", new Float(), new Int(), new Bool(), (x, y) -> new Bool(x.asDouble() <= y.asDouble()));
        setEvaluation("not greater", new Int(), new Float(), new Bool(), (x, y) -> new Bool(x.asDouble() <= y.asDouble()));
    }
    
    public SyntaxNode clone() {
        return new NoGreater(getChild(0),getChild(1));
    }

    public String getName() {
        return "not greater";
    }

}
