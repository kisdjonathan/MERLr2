package AST.baseTypes;


import AST.abstractNode.SyntaxNode;

import java.util.List;

public class FixedArray extends Storage{
    public FixedArray() {
        //TODO
    }
    public FixedArray(int size) {
        //TODO
    }
    public FixedArray(Tuple values) {
        setChildren(values.getChildren());
    }
    public FixedArray(List<SyntaxNode> values) {
        setChildren(values);
    }

    public boolean typeEquals(BasicType obj) {
        return false;
    }

    public DynamicArray clone() {
        return null;    //TODO
    }
}
