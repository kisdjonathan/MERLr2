package AST.control;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.DynamicArray;
import AST.baseTypes.FixedArray;

public class While extends Control {
    public While(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }
    public While(SyntaxNode condition, SyntaxNode body, SyntaxNode parent) {
        setParent(parent);
        setBase(condition, body);
    }
    private While(){}

    protected void setBase(Node node) {
        node.executionTrue = 0;
        super.setBase(node);
    }

    public While clone() {
        While ret = new While();
        ret.setParent(getParent());
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }

    @Override
    public BasicType getType() {
        DynamicArray ret = new DynamicArray();
        ret.setStoredType(getChild(0).getType());
        return ret;
    }

    @Override
    public BasicType interpret() {
        return getBase().interpret();
    }
}
