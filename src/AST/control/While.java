package AST.control;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class While extends Control {
    public While(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }
    public While(SyntaxNode condition, SyntaxNode body, SyntaxNode parent) {
        setParent(parent);
        setBase(condition, body);
    }
    private While(){}

    public While clone() {
        While ret = new While();
        ret.setBase(getBase().clone());
        return ret;
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
