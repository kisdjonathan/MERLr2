package AST.operations.variable;

import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

//builtin indexing provided for array/list types only
public class Index extends Operator {
    public Index(SyntaxNode ref, SyntaxNode pos) {
        setOrigin(ref);
        setVector(pos);
    }

    public SyntaxNode getDeclaredType() {
        //return getOrigin().getType().getIndexedType();
        return null;    //TODO
    }

    public String getName() {
        return "index";
    }
}
