package AST.baseTypes.advanced;

import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;

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
    public UnorderedMap emptyClone() {
        return new UnorderedMap();
    }
}
