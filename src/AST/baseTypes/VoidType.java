package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

public class VoidType extends BasicType {
    public String getName() {
        return "void";
    }

    public boolean typeEquals(BasicType other) {
        return true;
    }
    public VoidType clone() {
        return new VoidType();
    }
}
