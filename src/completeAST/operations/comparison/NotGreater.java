package completeAST.operations.comparison;

import derivedAST.FinalSyntaxNode;

public class NotGreater extends operations.comparison.ComparisonInfix {
    public NotGreater() {}
    public NotGreater(FinalSyntaxNode origin, FinalSyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "not greater";
    }
}
