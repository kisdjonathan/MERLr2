package type;

import AST.abstractNode.SyntaxNode;

public class InferredType extends Type{
    public String getName() {
        return "inferred";
    }


    public boolean isInferred() {
        return true;
    }

    public boolean typeEquals(Type obj) {
        return true;
    }

    public SyntaxNode emptyClone() {
        return new InferredType();
    }
}
