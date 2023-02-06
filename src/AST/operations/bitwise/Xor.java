package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Xor extends BinaryOperator {
    public Xor() {}
    public Xor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("xor");
        setEvaluation("xor", new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() != y.getValue()));
        setEvaluation("xor", new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() ^ y.getValue()));
    }

    public Xor clone() {
        return new Xor(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "xor";
    }
}
