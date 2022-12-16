package completeAST.operations.comparison;

import derivedAST.FinalSyntaxNode;

public class Greater extends operations.comparison.ComparisonInfix {
    public Greater() {}
    public Greater(FinalSyntaxNode origin, FinalSyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "greater";
    }
}
