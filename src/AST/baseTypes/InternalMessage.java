package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

public class InternalMessage extends BasicType{


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
}
