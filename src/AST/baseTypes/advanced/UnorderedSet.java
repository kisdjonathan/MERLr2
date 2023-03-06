package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import AST.components.Locality;

import java.util.Iterator;

//TODO fill in fields, methods, and bytes
public class UnorderedSet extends Storage {
    private static class FromValue extends UnorderedSet{
        public FromValue(SyntaxNode value) {
            addChild(value);
        }

        public BasicType getStoredType() {
            return ((Storage)getChild(0).getType()).getStoredType();
        }

        public UnorderedSet clone() {
            FromValue ret = new FromValue(getChild(0).clone());
            return ret;
        }

        public UnorderedSet interpret() {
            return castFrom(getChild(0).interpret());
        }

        public Iterator<SyntaxNode> asIterator() {
            throw new Error("unable to get compiled sequence from run-time set " + this);
        }

        public UnorderedSet emptyClone() {
            throw new Error("unable to get empty clone of from-value set " + this);
        }
    }

    public static UnorderedSet castFrom(SyntaxNode value) {
        if(value instanceof Tuple tvalue)
            return new UnorderedSet(tvalue);
        else if(value instanceof Storage svalue)
            return new UnorderedSet(svalue);
        else if(value.getType() instanceof Sequence || value.getType() instanceof Tuple)
            return new FromValue(value);
        throw new Error("unable to cast " + value + " to UnorderedSet");
    }

    public UnorderedSet(){}
    public UnorderedSet(Tuple values) {
        setChildren(values.getChildren());
    }
    public UnorderedSet(Storage values) {
        setChildren(values.getChildren());
        setStoredType(values.getStoredType());
    }

    public String getName() {
        return "storage";
    }

    public void unifyVariables(Locality variables) {
        super.unifyVariables(variables);
        if(size() > 0)
            setStoredType(getChild(0).getType());
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof UnorderedSet usother && getStoredType().typeEquals(usother.getStoredType());
    }

    public UnorderedSet clone() {
        UnorderedSet ret = new UnorderedSet();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }
    public UnorderedSet emptyClone() {
        return new UnorderedSet();
    }
}
