package AST.baseTypes.flagTypes;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class InternalMessage extends BasicType {


    public SyntaxNode clone() {
        return null;//TODO
    }

    private String message = "internal message";
    public String getName() {
        return message;
    }
    public void setName(String message) {
        this.message = message;
    }

    public boolean typeEquals(BasicType obj) {
        return false;//TODO
    }
    public String valueString() {
        return getName();
    }
}
