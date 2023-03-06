package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;

import java.util.List;

public class FixedArray extends Sequence{
    public FixedArray(){
    }
    public FixedArray(Tuple values) {
        setChildren(values.getChildren());
        if(values.size() > 0)
            setStoredType(values.getChild(0).getType());
    }
    public FixedArray(List<SyntaxNode> values) {
        setChildren(values);
        if(values.size() > 0)
            setStoredType(values.get(0).getType());
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof FixedArray daother && getStoredType().typeEquals(daother.getStoredType());
    }
    public FixedArray emptyClone() {
        return new FixedArray();
    }
}
