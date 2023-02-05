package AST.control;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.components.Variable;

import java.util.ArrayList;
import java.util.List;

public class While extends Control {
    private Variable conditionVariable = new Variable("condition"){{
        setType(new Bool(false));
    }};

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
        ret.setParent(getParent());
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
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
            values.add(body.interpret());
            conditionVariable.setType(condition.interpret());
            //TODO handle breaks and continues
        }
        if(conditionVariable.getType().equals(conditionControl.interpret())) {
            if (base.executionTrue > 0)  //strictly greater than 0, otherwise this would not make sense
                return getChild(base.executionTrue).interpret();
        }
        else if(base.executionFalse > 0)
            return getChild(base.executionFalse).interpret();

        return new Sequence(values);
    }

    public String toString() {
        return "while " + getChildren();
    }
}
