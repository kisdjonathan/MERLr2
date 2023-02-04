package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.List;

//TODO define storage (ie does it only store values of the same type...)
public abstract class Storage extends BasicType {
    public String getName() {
        return "storage";
    }

    private BasicType storedType = null;
    public void setStoredType(BasicType type) {
        storedType = type;
    }
    public BasicType getStoredType() {
        return storedType;
    }

    public List<SyntaxNode> getFields() {
        return null;    //TODO
    }

    public SyntaxNode getField(String name) {
        return null;    //TODO
    }
}
