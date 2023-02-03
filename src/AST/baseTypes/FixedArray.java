package AST.baseTypes;


import AST.abstractNode.SyntaxNode;

public class FixedArray extends Storage{
    public FixedArray(int size) {
        //TODO
    }
    public FixedArray(Tuple values) {
        //TODO
    }

    public boolean typeEquals(BasicType obj) {
        return false;
    }

    public DynamicArray clone() {
        return null;    //TODO
    }
}
