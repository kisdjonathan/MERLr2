package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.abstractNode.SyntaxNode;

public class Bool extends BasicType {
    public static final Bool TRUE = new Bool(true);
    public static final Bool FALSE = new Bool(false);
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

    public String toString() {
        return super.toString() + " " + value;
    }

    public boolean getValue(){
        return value;
    }

}
