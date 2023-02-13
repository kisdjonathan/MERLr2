package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;

//TODO fill in fields, methods, and bytes
public class UnorderedSet extends Storage {
    public UnorderedSet(){}
    public UnorderedSet(Tuple values) {
        setChildren(values.getChildren());
        if(values.size() > 0)
            setStoredType(values.getChild(0).getType());
    }

    public String getName() {
        return "storage";
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof UnorderedSet usother && getStoredType().typeEquals(usother.getStoredType());
    }
    public UnorderedSet clone() {
        UnorderedSet ret = new UnorderedSet();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }
    public UnorderedSet emptyClone() {
        return new UnorderedSet();
    }
}
