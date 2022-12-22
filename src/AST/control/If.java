package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class If extends Control {
    public If(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }

    public BasicType getType() {
        return getBase().getType();
    }

    public BasicType interpret() {
        return getBase().interpret();
    }
}
