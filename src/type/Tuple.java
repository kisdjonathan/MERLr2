package type;

import AST.abstractNode.SyntaxNode;
import AST.operator.binary.variable.As;
import compiler.Assembly;
import interpreter.MultiValue;
import interpreter.ReturnValue;
import interpreter.Value;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//Tuple represents an ordered comma or semicolon group
public class Tuple extends Type implements Iterable<SyntaxNode>{
    /**
     * returns node if node is a tuple, otherwise creates a tuple containing node and returns that
     **/
    public static Tuple asTuple(SyntaxNode node) {
        return node == null ? new Tuple() : node instanceof Tuple ? (Tuple)node : new Tuple(node);
    }

    public Tuple(){}
    public Tuple(List<SyntaxNode> children){
        setChildren(children);
    }
    public Tuple(SyntaxNode... nodes){
        setChildren(Arrays.asList(nodes));
    }

    public String getName() {
        return "tuple";
    }

    public Tuple emptyClone() {
        return new Tuple();
    }

    public Value interpret() {
        MultiValue ret = new MultiValue();
        for(SyntaxNode child : getChildren()) {
            Value value = child.interpret();
            if(value instanceof ReturnValue)
                return value;
            ret.addValue(value);
        }
        return ret;
    }

    public void compile(Assembly ret) {
        //TODO
        // for each child, generate its assembly and push onto commands, then shift top stack pointer up by the size of the value of the child
    }


    public Iterator<SyntaxNode> iterator() {
        return getChildren().listIterator();
    }

    public String valueString() {
        StringBuilder ret = new StringBuilder("(");
        for(SyntaxNode child : getChildren()) {
            if (ret.length() > 1)
                ret.append(",");
            ret.append(child.getType().valueString());
        }
        ret.append(")");
        return ret.toString();
    }
    public String toString() {
        StringBuilder ret = new StringBuilder("(");
        for(SyntaxNode child : getChildren()) {
            if (ret.length() > 1)
                ret.append(",");
            ret.append(child.toString());
        }
        ret.append(")");
        return ret.toString();
    }

    public void setType(Type type) {
        if(type instanceof Tuple tother && typeEquals(tother)) {
            for(int i = 0; i < size(); ++i) {
                if(getChild(i).getType() instanceof InferredType)
                    getChild(i).setType(tother.getChild(i).getType());
                else
                    throw new Error("unable to set type for tuple " + this + " to " + type);
            }
        }
        else
            throw new Error("unable to set type for tuple " + this + " to " + type);
    }

    public boolean typeEquals(Type other) {
        if(other instanceof Tuple tother) {
            if (other.size() != size())
                return false;
            for(int i = 0; i < size(); ++i)
                if(!getChild(i).getType().typeEquals(tother.getChild(i).getType()))
                    return false;
            return true;
        }
        return false;
    }

    public boolean equals(Object other) {
        if(other instanceof Tuple tother) {
            for(int i = 0; i < size(); ++i)
                if(!getChild(i).equals(tother.getChild(i)))
                    return false;
            return true;
        }
        return false;
    }
}