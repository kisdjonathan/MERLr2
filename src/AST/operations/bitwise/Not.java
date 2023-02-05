package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Bool;
import AST.baseTypes.Int;
import AST.operations.UnaryOperator;

public class Not extends UnaryOperator {
    public Not(){}

    static {
        setEvaluation(new Bool(), new Bool(), (x) -> new Bool(!x.getValue()));
        setEvaluation(new Int(), new Int(), (x) -> new Int(~x.getValue()));
    }

    public Not(SyntaxNode value) {
        addChild(value);
    }

    public Not clone() {
        return new Not(getChild(0).clone());
    }

    public String getName() {
        return "not";
    }
}
