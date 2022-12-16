package completeAST.operations.comparison;

import derivedAST.FinalSyntaxNode;

public class Equal extends operations.comparison.ComparisonInfix {
    public Equal() {}
    public Equal(FinalSyntaxNode origin, FinalSyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "equal";
    }
}
