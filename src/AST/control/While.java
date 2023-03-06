package AST.control;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.advanced.Sequence;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.numerical.Bool;
import AST.components.Locality;
import AST.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class While extends Control {
    private Variable conditionVariable = new Variable("condition"){{
        setType(new Bool(false));
    }};

    public While(SyntaxNode condition, SyntaxNode body) {
        putVariable(conditionVariable.getName(), conditionVariable.getEntry());
        setBase(condition, body);
    }
    private While(){
        putVariable(conditionVariable.getName(), conditionVariable.getEntry());
    }

    public void unifyVariables(Locality variables) {
        Locality.Layer localLayer = new Locality.Layer(variables);
        localLayer.putVariable(conditionVariable.getName(), conditionVariable.getEntry());
        super.unifyVariables(localLayer);
        getVariables().putAll(localLayer.getVariables());
    }

    public While clone() {
        While ret = new While();
        ret.setParent(getParent());
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.unifyVariables(new Locality.Wrapper(getVariableClones()));
        return ret;
    }

    public BasicType getType() {
        Node base = getBase();
        if(base.executionFalse > 0 && base.executionTrue > 0)
            return getChild(base.executionFalse).getType();

        Sequence ret = new Sequence();
        ret.setStoredType(getChild(0).getType());
        return ret;
    }

    public BasicType interpret() {
        List<SyntaxNode> values = new ArrayList<>();    //TODO optimize storing values such that it is not used when both success and break conditions are fulfilled
        Node base = getBase();
        SyntaxNode condition = base.getChild(0), body = base.getChild(1);

        conditionVariable.setType(condition.interpret());
        while(conditionVariable.getType().equals(conditionControl.interpret())){
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
                    for(int i = 0; i < c.getLayers(); ++i)
                        conditionVariable.setType(condition.interpret());
                    continue;
                }
            }
            values.add(value);
            conditionVariable.setType(condition.interpret());
        }
        if(conditionVariable.getType().equals(conditionControl.interpret())) {
            if (base.executionTrue > 0)  //strictly greater than 0, otherwise this would not make sense
                return getChild(base.executionTrue).interpret();
        }
        else if(base.executionFalse > 0)
            return getChild(base.executionFalse).interpret();

        return new Sequence(values).flatten();
    }

    public String toString() {
        return "while " + getChildren();
    }
}
