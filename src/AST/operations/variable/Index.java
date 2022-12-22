package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

//builtin indexing provided for array/list types only
public class Index extends Operator {
    public Index(SyntaxNode ref, SyntaxNode pos) {
        addChild(ref); addChild(pos);
    }

    public SyntaxNode getDeclaredType() {
        //return getOrigin().getType().getIndexedType();
        return null;    //TODO
    }

    public String getName() {
        return "index";
    }

    //TODO
    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
