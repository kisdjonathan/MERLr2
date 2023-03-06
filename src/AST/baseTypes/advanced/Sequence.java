package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.flagTypes.ReturnCode;
import AST.baseTypes.numerical.Int;
import AST.components.Signature;
import AST.components.Variable;

import java.util.Iterator;
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
    private class Append extends ReturnCode {
        public Append(SyntaxNode index) {
            addChild(index);
        }

        public BasicType getValue() {
            BasicType interpretedValue = getChild(0).interpret();
            Sequence.this.addChild(interpretedValue);
            return new VoidType();
        }

        public Append clone() {
            return new Append(getChild(0).clone());
        }
    }
    public Sequence(){
        //interpreter
        Signature remove = new Signature("remove");
        putVariable(remove.getName(), remove);
        {
            Variable removed = new Variable("removed"); removed.setType(new Int());
            Function removal = new Function(Tuple.asTuple(removed), Tuple.asTuple(new InferredType()));
            removal.addChild(new RemoveIndex(removed));
            //removal.unifyVariables(variables);
            remove.addOverload(removal);
        }

        Signature append = new Signature("append");
        putVariable(append.getName(), append);
        {
            Variable appended = new Variable("appended"); appended.setType(new InferredType());
            Function appending = new Function(Tuple.asTuple(appended), Tuple.asTuple(new InferredType()));
            appending.addChild(new Append(appended));
            //removal.unifyVariables(variables);
            append.addOverload(appending);
        }
    }
    public Sequence(Tuple values) {
        this();
        setChildren(values.getChildren());
        if(values.size() > 0)
            setStoredType(values.getChild(0).getType());
    }
    public Sequence(List<SyntaxNode> values) {
        this();
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

    public Sequence clone() {
        Sequence ret = emptyClone();
        for(SyntaxNode child : ret.getChildren())
            ret.addChild(child);
        return ret;
    }
    public Sequence emptyClone() {
        return new Sequence();
    }
}
