package AST.baseTypes;

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

    public Tuple getRets() {
        return rets;
    }
    public void setRets(SyntaxNode second) {
        rets = Tuple.asTuple(second);
        rets.setParent(this);
    }

    public boolean equals(Object other) {
        if(!(other instanceof Signature fother))
            return false;
        return args.equals(fother.getArgs()) && rets.equals(fother.getRets());
    }
}
