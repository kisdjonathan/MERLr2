package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class BitNot extends BitwiseOperator {
    public BitNot(){}
    public BitNot(SyntaxNode value) {
        addChild(value);
    }

    public String getName() {
        return "bit not";
    }
}
