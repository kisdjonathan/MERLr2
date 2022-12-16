package completeAST.operations.bitwise;

import derivedAST.FinalSyntaxNode;
import operations.BuiltinOperation;

public class BitwiseInfix extends BuiltinOperation {
    public BitwiseInfix() {}
    public BitwiseInfix(FinalSyntaxNode a, FinalSyntaxNode b) {
        setOrigin(a);
        setVector(b);
    }
}
