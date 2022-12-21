package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;

public class BitNot extends BitwiseInfix {
    public BitNot(){}
    public BitNot(SyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "bit not";
    }
}
