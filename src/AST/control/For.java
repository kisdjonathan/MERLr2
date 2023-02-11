package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import AST.components.*;

import java.util.Map;

public class For extends Control {

    /**
     * formatted as such:
     *  for iterationVariables in iterableVariable:
     *      body
     */
    public For(SyntaxNode iterationVariables, SyntaxNode iterableVariable, SyntaxNode body) {
        iterationVariables.setParent(this);
        for(SyntaxNode node : Tuple.asTuple(iterableVariable)){
            node.setParent(this);
            if(!(node instanceof Variable))
                throw new Error("for loop with non-variable variable");
            addChild(node);
        }
        setBase(iterableVariable, body);
    }

    private For(){}

    public void unifyVariables(Map<String, Variable> variables) {
        getVariables().putAll(variables);
        //this.variables.put(counter.getName(), counter);
        super.unifyVariables(getVariables());
    }

    public For clone() {
        For ret = new For();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.unifyVariables(getVariableClones());
        return ret;
    }

    @Override
    public BasicType getType() {
        return null;    //TODO
    }

    @Override
    public BasicType interpret() {
        return null;
    }

    public String toString() {
        return "for " + getChildren();
    }
}
