package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.operations.BinaryOperator;

public class RightShift extends BinaryOperator {
    public RightShift() {}
    public RightShift(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static {
        //TODO
    }

    public RightShift clone() {
        return new RightShift(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "right shift";
    }
}
