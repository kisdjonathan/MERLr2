package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.flagTypes.ControlCode;

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

    public BasicType interpret(){
        Storage ret = this.emptyClone();
        for(SyntaxNode child : getChildren()) {
            BasicType value = child.interpret();
            if(value instanceof ControlCode)
                return value;
            ret.addChild(value);
        }
        return ret;
    }

    //Interpreter function
    public abstract Sequence asSequence();

    public abstract Storage emptyClone();

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
