package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;

import java.util.ArrayList;
import java.util.List;

//TODO define storage (ie does it only store values of the same type...)
public abstract class Storage extends BasicType {
    public String getName() {
        return "storage";
    }

    private BasicType storedType = new InferredType();
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

    public String valueString() {
        List<String> values = new ArrayList<>();
        for(SyntaxNode child : getChildren())
            values.add(child.getType().valueString());
        return values.toString();
    }
    public String toString() {
        return getName() + " " + getChildren();
    }
}
