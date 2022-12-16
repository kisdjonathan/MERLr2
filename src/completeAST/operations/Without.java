package completeAST.operations;

import derivedAST.FinalSyntaxNode;

public class Without extends operations.With {
    public Without(){}

    public Without(FinalSyntaxNode add, FinalSyntaxNode val){
        setOrigin(add);
        setVector(val);
    }

}
