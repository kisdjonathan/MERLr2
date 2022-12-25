package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.abstractNode.SyntaxNode;

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

    public Bool clone() {
        return new Bool(value);
    }

    public String toString() {
        return super.toString() + " " + value;
    }

    public boolean getValue(){
        return value;
    }

}
