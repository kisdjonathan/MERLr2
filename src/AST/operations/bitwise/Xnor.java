package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Xnor extends BinaryOperator {
    public Xnor() {}
    public Xnor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("xnor");
        setEvaluation("xnor", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() == y.getValue()));
        setEvaluation("xnor", new Int(), new Int(), new Int(), (x, y) -> new Int(~(x.getValue() ^ y.getValue())));
    }

    public Xnor clone() {
        return new Xnor(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "xnor";
    }
}
