package completeAST.baseTypes;

import completeAST.abstractNode.FunctionalNode;
import derivedAST.FinalSyntaxNode;

public class Bool extends BasicType {
    private boolean value = false;

    public Bool(){}
    public Bool(boolean val) {
        value = val;
    }
    public Bool(String val) {
        //TODO val is string literal "true"/"false" or 0,1
        value = Boolean.parseBoolean(val);
    }

    public String getName() {
        return "bool";
    }

    public boolean typeEquals(FunctionalNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) == 0;
    }
    public boolean typeContains(FunctionalNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) >= 0;
    }

    public TypeSize getByteSize() {
        return new TypeSize(1);
    }

    public String toString() {
        return super.toString() + " " + value;
    }
}
