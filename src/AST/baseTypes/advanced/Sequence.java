package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.flagTypes.ReturnCode;
import AST.baseTypes.numerical.Int;
import AST.variables.Signature;
import AST.variables.Variable;
import AST.variables.VariableEntry;

import java.util.List;

public class Sequence extends Storage{
    private class RemoveIndex extends ReturnCode {
        public RemoveIndex(SyntaxNode index) {
            addChild(index);
        }

        public BasicType getValue() {
            Int interpretedValue = (Int)getChild(0).interpret();
            Sequence.this.removeChild(interpretedValue.getValue());
            return new VoidType();
        }

        public RemoveIndex clone() {
            return new RemoveIndex(getChild(0).clone());
        }
    }
    public Sequence(){
        //interpreter
        VariableEntry remove = new VariableEntry();
        putField("remove", remove);
        {
            Variable removed = new Variable("removed");
            Function removal = new Function(Tuple.asTuple(removed), Tuple.asTuple(new InferredType()));
            removal.addChild(new RemoveIndex(removed));
            //removal.unifyVariables(variables);
            remove.addOverload(removal);
        }
    }
    public Sequence(Tuple values) {
        setChildren(values.getChildren());
        if(values.size() > 0)
            setStoredType(values.getChild(0).getType());
    }
    public Sequence(List<SyntaxNode> values) {
        setChildren(values);
        if(values.size() > 0)
            setStoredType(values.get(0).getType());
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Sequence daother && getStoredType().typeEquals(daother.getStoredType());
    }

    public Sequence flatten() {
        Sequence ret = new Sequence();
        for(SyntaxNode child : getChildren()) {
            BasicType value = (BasicType) child;
            if(value instanceof Sequence seqvalue)
                for(SyntaxNode otherChild : seqvalue.flatten().getChildren())
                    ret.addChild(otherChild);
            else
                ret.addChild(value);
        }
        return ret;
    }

    public Sequence emptyClone() {
        return new Sequence();
    }
}
