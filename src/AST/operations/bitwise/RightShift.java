package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class RightShift extends BitwiseOperator {
    public RightShift() {}
    public RightShift(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "right shift";
    }
}
