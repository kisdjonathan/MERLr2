package AST.baseTypes;

import baseAST.abstractNode.SyntaxNode;
import AST.abstractNode.SyntaxNode;

public class Signature extends BasicType{
    private Tuple args = null, rets = null;

    public Signature() {}
    public Signature(Tuple args, Tuple rets) {
        this.args = args;
        this.rets = rets;
    }

    public String getName() {
        return "signature";
    }


    public Tuple getArgs() {
        return args;
    }
    public void setArgs(SyntaxNode first) {
        args = Tuple.asTuple(first);
        args.setParent(this);
    }
    public void setArgs(SyntaxNode first) {
        first.setParent(this);
        setArgs(first.getEvaluatedReplacement());
    }

    public Tuple getRets() {
        return rets;
    }
    public void setRets(SyntaxNode second) {
        rets = Tuple.asTuple(second);
        rets.setParent(this);
    }
    public void setRets(SyntaxNode second) {
        second.setParent(this);
        setRets(second.getEvaluatedReplacement());
    }


    public boolean typeEquals(SyntaxNode other) {
        if(!(other instanceof Signature fother))
            return false;
        return args.typeEquals(fother.getArgs()) && rets.typeEquals(fother.getRets());
    }

    public TypeSize getByteSize() {
        return new TypeSize();
    }
}
