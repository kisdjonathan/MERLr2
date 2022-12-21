package AST.baseTypes;


//TODO special type here

import AST.abstractNode.SyntaxNode;

import java.util.Iterator;
import java.util.List;

//Tuple represents an ordered comma or semicolon group
public class Tuple extends BasicType implements Iterable<SyntaxNode>{
    /**
     * returns node if node is a tuple, otherwise creates a tuple containing node and returns that
     **/
    public static Tuple asTuple(SyntaxNode node) {
        return node instanceof Tuple ? (Tuple)node : new Tuple(){{
            addChild(node);}};
    }

    public Tuple(){}
    public Tuple(List<SyntaxNode> children){
        setChildren(children);
    }

    public String getName() {
        return "tuple";
    }


    public boolean typeEquals(SyntaxNode t) {
        for(SyntaxNode child : children)
            if(!child.typeEquals(t))
                return false;
        return true;
    }

    public TypeSize getByteSize() {
        TypeSize ret = new TypeSize(0);
        for(SyntaxNode child : children)
            ret = TypeSize.add(ret, child.getBaseType().getByteSize());
        return ret;
    }
    public SyntaxNode newInstance(String s) {
        throw new Error("unable to create new tuple instance as a literal (from " + s + ")");
    }


    public Iterator<SyntaxNode> iterator() {
        return children.listIterator();
    }

    public String toString() {
        StringBuilder ret = new StringBuilder("(");
        for(SyntaxNode child : children) {
            if (ret.length() > 1)
                ret.append(",");
            ret.append(child.toString());
        }
        ret.append(")");
        return ret.toString();
    }
}
