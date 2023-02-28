package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;

import java.util.Iterator;

public class DynamicArray extends Sequence{
    private static class FromValue extends DynamicArray{
        public FromValue(SyntaxNode value) {
            addChild(value);
        }

        public BasicType getStoredType() {
            return ((Storage)getChild(0).getType()).getStoredType();
        }

        public DynamicArray clone() {
            FromValue ret = new FromValue(getChild(0).clone());
            return ret;
        }

        public DynamicArray interpret() {
            return castFrom(getChild(0).interpret());
        }

        public Iterator<SyntaxNode> asIterator() {
            throw new Error("unable to get compiled sequence from run-time array " + this);
        }

        public DynamicArray emptyClone() {
            throw new Error("unable to get empty clone of from-value array " + this);
        }
    }
    private static class FromIterator extends DynamicArray{
        public FromIterator(SyntaxNode value) {
            addChild(value);
        }

        public BasicType getStoredType() {
            return ((Container)getChild(0).getType()).getStoredType();
        }

        public DynamicArray clone() {
            FromIterator ret = new FromIterator(getChild(0).clone());
            return ret;
        }

        public DynamicArray interpret() {
            DynamicArray ret = new DynamicArray();
            Iterator<SyntaxNode> iterator = ((Container)getChild(0).interpret()).asIterator();
            while(iterator.hasNext())
                ret.addChild(iterator.next());
            return ret;
        }

        public Iterator<SyntaxNode> asIterator() {
            throw new Error("unable to get compiled sequence from run-time array " + this);
        }
        public FromIterator emptyClone() {
            throw new Error("unable to get empty clone of from-value array " + this);
        }
    }

    public static DynamicArray castFrom(SyntaxNode value) {
        if(value instanceof Tuple tvalue)
            return new DynamicArray(tvalue);
        else if(value instanceof Storage svalue)
            return new DynamicArray(svalue);
        else if(value.getType() instanceof Sequence || value.getType() instanceof Tuple)
            return new FromValue(value);
        else if(value.getType() instanceof Container)
            return new FromIterator(value);
        throw new Error("unable to cast " + value + " to DynamicArray");
    }

    public DynamicArray(){
    }
    public DynamicArray(Tuple values) {
        setChildren(values.getChildren());
        if(values.size() > 0)
            setStoredType(values.getChild(0).getType());
    }
    public DynamicArray(Storage values) {
        setChildren(values.getChildren());
        setStoredType(values.getStoredType());
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof DynamicArray daother && getStoredType().typeEquals(daother.getStoredType());
    }
    public DynamicArray clone() {
        DynamicArray ret = new DynamicArray();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }
    public DynamicArray emptyClone() {
        return new DynamicArray();
    }
}
