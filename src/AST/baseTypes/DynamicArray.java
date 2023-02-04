package AST.baseTypes;


import AST.abstractNode.SyntaxNode;

public class DynamicArray extends Storage{
    public DynamicArray(){
        //TODO
    }
    public DynamicArray(Tuple values) {
        setChildren(values.getChildren());
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof DynamicArray daother && getStoredType().typeEquals(daother.getStoredType());
    }
    public DynamicArray clone() {
        DynamicArray ret = new DynamicArray();
        for(SyntaxNode child : ret.getChildren())
            ret.addChild(child);
        return ret;
    }
}
