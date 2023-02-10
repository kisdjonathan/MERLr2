package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.advanced.Sequence;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.numerical.Int;
import AST.baseTypes.numerical.Numerical;
import AST.components.Variable;
import AST.operations.BinaryOperator;
import AST.operations.Operator;
import AST.operations.With;
import AST.operations.arithmetic.PreIncrement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repeat extends Control {
    private Variable counter = new Variable("repetitions"){{
        setType(new Int(0));
    }};

    public Repeat(SyntaxNode count, SyntaxNode body) {
        putVariable(counter.getName(), counter);
        setBase(count, body);
    }
    private Repeat(){
        putVariable(counter.getName(), counter);
    }

    protected void setBase(Node node) {
        super.setBase(node);
    }

    public BasicType getType() {
        Sequence ret = new Sequence();
        ret.setStoredType(getChild(0).getType());
        return ret;
    }

    public Repeat clone() {
        Repeat ret = new Repeat();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.unifyVariables();
        return ret;
    }

    public BasicType interpret() {
        counter.setType(new Int(0));
        List<SyntaxNode> values = new ArrayList<>();
        Node base = getBase();
        SyntaxNode condition = base.getChild(0), body = base.getChild(1);

        while(((Int)counter.getType()).getValue() < ((Numerical)condition.interpret()).asDouble()){ //TODO do something about the length of this condition (and in line 72)
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
                    Int counterValue = (Int)counter.getType();
                    counterValue.setValue(counterValue.getValue() + c.getLayers());
                }
            }
            values.add(value);
            PreIncrement.increment(counter.getType());
        }
        if(((Int)counter.getType()).getValue() < ((Numerical)condition.interpret()).asDouble()) {
            if (base.executionTrue > 0)  //strictly greater than 0, otherwise this would not make sense
                return getChild(base.executionTrue).interpret();
        }
        else if(base.executionFalse > 0)
            return getChild(base.executionFalse).interpret();

        return new Sequence(values);
    }

    public String toString() {
        return "repeat " + getChildren();
    }
}
