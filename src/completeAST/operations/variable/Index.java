package completeAST.operations.variable;

import completeAST.operations.BuiltinOperation;
import derivedAST.FinalSyntaxNode;

//builtin indexing provided for array/list types only
public class Index extends BuiltinOperation {
    public Index(FinalSyntaxNode ref, FinalSyntaxNode pos) {
        setOrigin(ref);
        setVector(pos);
    }

    public FinalSyntaxNode getDeclaredType() {
        //return getOrigin().getType().getIndexedType();
        return null;    //TODO
    }

    public String getName() {
        return "index";
    }
}
