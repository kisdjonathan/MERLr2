package AST.baseTypes;


//TODO special type here

import AST.abstractNode.SyntaxNode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//Tuple represents an ordered comma or semicolon group
public class Tuple extends BasicType implements Iterable<SyntaxNode>{
    /**
     * returns node if node is a tuple, otherwise creates a tuple containing node and returns that
     **/
    public static Tuple asTuple(SyntaxNode node) {
        return node == null ? new Tuple() : node instanceof Tuple ? (Tuple)node : new Tuple(Arrays.asList(node));
    }

    public Tuple(){}
    public Tuple(List<SyntaxNode> children){
        setChildren(children);
    }

    public String getName() {
        return "tuple";
    }


    public Iterator<SyntaxNode> iterator() {
        return getChildren().listIterator();
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
}
