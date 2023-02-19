package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import AST.baseTypes.advanced.Sequence;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.numerical.Int;
import AST.components.*;
import AST.operations.variable.In;

import java.util.ArrayList;
import java.util.List;

public class For extends Control {
    /**
     * formatted as such:
     *  for iterationVariables in iterableVariable:
     *      body
     */
    public For(In iterStatement, SyntaxNode body) {
        setBase(new Tuple(iterStatement.getChildren()), body);
    }
    private For(){}

    public BasicType getType() {
        Sequence ret = new Sequence();
        ret.setStoredType(getChild(0).getType());
        return ret;
    }

    public void unifyVariables(Locality variables) {
        Locality.Layer localLayer = new Locality.Layer(variables);
        //localLayer.putVariable(controlVariable.getName(), controlVariable);
        super.unifyVariables(localLayer);
        getVariables().putAll(localLayer.getVariables());
    }

    public For clone() {
        For ret = new For();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.unifyVariables(new Locality.Wrapper(getVariableClones()));
        return ret;
    }

    public BasicType interpret() {
        //TODO currently, for loops only iterate for 1 variable, but it should be like python
        //TODO for loops should iterate over iterables, and not sequences themselves
        Node base = getBase();
        SyntaxNode condition = base.getChild(0), body = base.getChild(1);
        Variable iterVar = condition.getChild(0).asVariable();
        BasicType iterParent = condition.getChild(1).interpret();

        List<SyntaxNode> values = new ArrayList<>();

        int i = 0;
        while(i < iterParent.size()) {
            iterVar.setType(iterParent.getChild(i).interpret());
            BasicType value = body.interpret();
            if(value instanceof ControlCode c) {
                if(c.getChoice() == ControlCode.BREAK && c.getLayers() > 0) {
                    if(c.getLayers() > 1)
                        return c.reduced();
                    break;
                }
                else if(c.getChoice() == ControlCode.RETURN)
                    return c;
                else if(c.getChoice() == ControlCode.CONTINUE) {
                    i += c.getLayers();   //-1 because this is in a for loop
                    continue;
                }
            }
            values.add(value);
            ++i;
        }
        if(i >= iterParent.size()) {
            if (base.executionTrue > 0)  //strictly greater than 0, otherwise this would not make sense
                return getChild(base.executionTrue).interpret();
        }
        else if(base.executionFalse > 0)
            return getChild(base.executionFalse).interpret();

        return new Sequence(values);
    }

    public String toString() {
        return "for " + getChildren();
    }
}
