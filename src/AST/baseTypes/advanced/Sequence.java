package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;

import java.util.List;

public class Sequence extends Storage{
    public Sequence(){
        //TODO
    }
    public Sequence(Tuple values) {
        setChildren(values.getChildren());
        if(values.size() > 0)
            setStoredType(values.getChild(0).getType());
    }
    public Sequence(List<SyntaxNode> values) {
        setChildren(values);
        if(values.size() > 0)
            setStoredType(values.get(0).getType());
    }

    public Sequence asSequence() {
        return this;
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Sequence daother && getStoredType().typeEquals(daother.getStoredType());
    }

    public Sequence clone() {
        Sequence ret = new Sequence();
        for(SyntaxNode child : ret.getChildren())
            ret.addChild(child);
        return ret;
    }
    public Sequence emptyClone() {
        return new Sequence();
    }
}
