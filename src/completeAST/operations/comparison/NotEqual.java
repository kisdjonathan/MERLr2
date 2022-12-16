package completeAST.operations.comparison;

import derivedAST.FinalSyntaxNode;

public class NotEqual extends operations.comparison.ComparisonInfix {
    public NotEqual() {}
    public NotEqual(FinalSyntaxNode origin, FinalSyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "not equal";
    }
}
