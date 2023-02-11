package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.components.Variable;

import java.util.Map;

public class If extends Control {
    public If(SyntaxNode condition, SyntaxNode body) {
        setBase(condition, body);
    }
    private If(){}

    public BasicType getType() {
        return getBase().getType();
    }

    public void unifyVariables(Map<String, Variable> variables) {
        getVariables().putAll(variables);
        super.unifyVariables(getVariables());
    }

    public If clone() {
        If ret = new If();
        ret.setParent(getParent());
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.unifyVariables(getVariableClones());
        return ret;
    }

    public BasicType interpret() {
        return getBase().interpret();
    }

    public String toString() {
        return "if " + getChildren();
    }
}
