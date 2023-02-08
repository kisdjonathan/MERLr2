package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.operations.Operator;

public class LeftShift extends Operator {
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
