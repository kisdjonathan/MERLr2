package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.advanced.Sequence;
import AST.baseTypes.numerical.Int;
import AST.components.Variable;
import AST.operations.With;
import AST.operations.arithmetic.PreIncrement;

import java.util.ArrayList;
import java.util.List;

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
        node.setChild(1, new With(node.getChild(1), new PreIncrement(counter)));
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
        return ret;
    }

    public BasicType interpret() {
        List<SyntaxNode> values = new ArrayList<>();    //TODO optimize storing values such that it is not used when both success and break conditions are fulfilled
        Node base = getBase();
        SyntaxNode condition = base.getChild(0), body = base.getChild(1);

        while(!counter.getType().equals(condition.interpret())){
            values.add(body.interpret());
        }
        if(!counter.getType().equals(condition.interpret())) {
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
