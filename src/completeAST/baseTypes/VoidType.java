package completeAST.baseTypes;

import derivedAST.FinalSyntaxNode;

public class VoidType extends BasicType {
    public String getName() {
        return "void";
    }

    public TypeSize getByteSize() {
        return new TypeSize(0);
    }
    public FinalSyntaxNode newInstance(String s) {
        return new VoidType();
    }
}
