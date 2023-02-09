package AST.baseTypes;


//TODO special type here

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.flagTypes.InternalMessage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//Tuple represents an ordered comma or semicolon group
public class Tuple extends BasicType implements Iterable<SyntaxNode>{
    /**
     * returns node if node is a tuple, otherwise creates a tuple containing node and returns that
     **/
    public static Tuple asTuple(SyntaxNode node) {
        return node == null ? new Tuple() : node instanceof Tuple ? (Tuple)node : new Tuple() {{addChild(node);}};
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

    public Tuple clone() {
        Tuple ret = new Tuple();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }

    @Override
    public BasicType interpret() {
        Tuple ret = new Tuple();
        for(SyntaxNode child : getChildren()) {
            BasicType value = child.interpret();
            if(value instanceof ControlCode)
                return value;
            ret.addChild(value);
        }
        return ret;
    }

    public Iterator<SyntaxNode> iterator() {
        return getChildren().listIterator();
    }

    public String valueString() {
        StringBuilder ret = new StringBuilder("(");
        for(SyntaxNode child : getChildren()) {
            if (ret.length() > 1)
                ret.append(",");
            ret.append(child.getType().toString());
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

    public boolean typeEquals(BasicType other) {
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

