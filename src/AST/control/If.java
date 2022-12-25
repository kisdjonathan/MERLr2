package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class If extends Control {
    public If(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }
    private If(){}

    public BasicType getType() {
        return getBase().getType();
    }

    public If clone() {
        If ret = new If();
        ret.setBase(getBase().clone());
        return ret;
    }

    public BasicType interpret() {
        return getBase().interpret();
    }
}
