package completeAST.operations.bitwise;

import derivedAST.FinalSyntaxNode;

public class BitNot extends BitwiseInfix {
    public BitNot(){}
    public BitNot(FinalSyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "bit not";
    }
}
