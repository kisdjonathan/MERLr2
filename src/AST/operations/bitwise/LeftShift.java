package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.operations.BinaryOperator;

public class LeftShift extends BinaryOperator {
    public LeftShift() {}
    public LeftShift(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static {
        //TODO
    }

    public LeftShift clone() {
        return new LeftShift(getChild(0).clone(), getChild(1).clone());
    }

    public String getName() {
        return "left shift";
    }
}
