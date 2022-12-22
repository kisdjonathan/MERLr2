package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class Repeat extends Control {
    public Repeat(SyntaxNode count, SyntaxNode body) {
        setBase(count, body);
    }

    //TODO
    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
