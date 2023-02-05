package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.List;

public class Sequence extends Storage{
    public Sequence(){
        //TODO
    }
    public Sequence(Tuple values) {
        setChildren(values.getChildren());
    }
    public Sequence(List<SyntaxNode> values) {
        setChildren(values);
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof DynamicArray daother && getStoredType().typeEquals(daother.getStoredType());
    }
    public Sequence clone() {
        Sequence ret = new Sequence();
        for(SyntaxNode child : ret.getChildren())
            ret.addChild(child);
        return ret;
    }
}
