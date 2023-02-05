package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Int;
import AST.operations.BinaryOperator;

public class Xor extends BinaryOperator {
    public Xor() {}
    public Xor(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static {
        setEvaluation(new Bool(), new Bool(), new Bool(), (x, y) -> new Bool(x.getValue() != y.getValue()));
        setEvaluation(new Int(), new Int(), new Int(), (x, y) -> new Int(x.getValue() ^ y.getValue()));
    }

    public Xor clone() {
        return new Xor(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "xor";
    }
}
