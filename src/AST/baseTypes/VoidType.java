package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

public class VoidType extends BasicType {
    public String getName() {
        return "void";
    }

    public TypeSize getByteSize() {
        return new TypeSize(0);
    }
    public SyntaxNode newInstance(String s) {
        return new VoidType();
    }
}
