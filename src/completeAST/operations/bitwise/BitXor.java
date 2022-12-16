package completeAST.operations.bitwise;

import derivedAST.FinalSyntaxNode;

public class BitXor extends operations.bitwise.BitwiseInfix {
    public BitXor() {}
    public BitXor(FinalSyntaxNode origin, FinalSyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "bit xor";
    }
}
