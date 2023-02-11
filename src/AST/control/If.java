package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.components.Locality;

public class If extends Control {
    public If(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }
    private If(){}

    public BasicType getType() {
        return getBase().getType();
    }

    public void unifyVariables(Locality variables) {
        Locality.Layer localLayer = new Locality.Layer(variables);
        super.unifyVariables(localLayer);
        getVariables().putAll(localLayer.getVariables());
    }

    public If clone() {
        If ret = new If();
        ret.setParent(getParent());
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.unifyVariables(new Locality.Wrapper(getVariableClones()));
        return ret;
    }

    public BasicType interpret() {
        return getBase().interpret();
    }

    public String toString() {
        return "if " + getChildren();
    }
}
