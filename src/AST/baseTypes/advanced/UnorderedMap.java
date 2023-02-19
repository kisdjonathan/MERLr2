package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;

import java.util.Iterator;

public class UnorderedMap extends Storage {
    public UnorderedMap(){}
    public UnorderedMap(Tuple values) {
        //TODO
    }

    public boolean typeEquals(BasicType other) {
        return false;
    }
    public UnorderedMap clone() {
        return null;    //TODO
    }

    public Iterator<SyntaxNode> asIterator() {
        return null;    //TODO
    }

    public UnorderedMap emptyClone() {
        return new UnorderedMap();
    }
}
