package completeAST.operations.variable;

import completeAST.operations.BuiltinOperation;
import derivedAST.FinalSyntaxNode;

public class Copy extends BuiltinOperation {
    public Copy(FinalSyntaxNode v) {
        setOrigin(v);
    }

    public String getName() {
        return "copy";
    }
}
