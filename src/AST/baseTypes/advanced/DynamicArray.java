package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;

import java.util.List;

public class DynamicArray extends Storage{
    public DynamicArray(){
        //TODO
    }
    public DynamicArray(Tuple values) {
        setChildren(values.getChildren());
        if(values.size() > 0)
            setStoredType(values.getChild(0).getType());
    }
    public DynamicArray(List<SyntaxNode> values) {
        setChildren(values);
        if(values.size() > 0)
            setStoredType(values.get(0).getType());
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
