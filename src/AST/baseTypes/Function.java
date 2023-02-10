package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.flagTypes.ReturnCode;
import AST.components.Variable;

import java.util.HashMap;
import java.util.Map;

public class Function extends BasicType {
    private Tuple args = null, rets = null;
    private Tuple rargs = null, rrets = null;
    private Map<String, Variable> variables = new HashMap<>();

    public Function() {}
    public Function(Tuple args, Tuple rets) {
        this.args = args;
        this.rets = rets;
        rargs = args.clone();
        rrets = rets.clone();
    }

    public String getName() {
        return "function";
    }


    public Tuple getArgs() {
        return args;
    }
    public void setArgs(SyntaxNode first) {
        args = Tuple.asTuple(first);
        args.setParent(this);
        rargs = args.clone();
    }

    public Tuple getRets() {
        return rets;
    }
    public void setRets(SyntaxNode second) {
        rets = Tuple.asTuple(second);
        rets.setParent(this);
        rrets = rets.clone();
    }

    public Function clone() {
        Function ret = new Function(args.clone(), rets.clone());
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }

    public void unifyVariables(Map<String, Variable> variables) {
        this.variables.putAll(variables);
        for(SyntaxNode arg : args) {
            Variable var = arg.asVariable();
            this.variables.put(var.getName(), var);
        }
        for(SyntaxNode ret : rets) {
            if(ret instanceof BasicType)
                break;
            Variable var = ret.asVariable();
            this.variables.put(var.getName(), var);
        }
        super.unifyVariables(this.variables);

        rargs = args.clone();
        rrets = rets.clone();
    }

    public BasicType interpretExecute(Tuple args) {
        //reset original conditions
        this.args = rargs.clone();
        this.rets = rrets.clone();

        this.args.interpret();
        for(int i = 0; i < args.size(); ++i)
            this.args.getChild(i).asVariable().setType(args.getChild(i).getType());
        if(rets.size() == 0) {
            getChild(0).interpret();
            return new VoidType();
        }
        else if(rets.size() == 1 && rets.getChild(0) instanceof InferredType) {
            BasicType value = getChild(0).interpret();
            if(value instanceof ReturnCode retVal)
                return retVal.getValue();
            else
                return value;
        }
        else{
            this.rets.interpret();
            getChild(0).interpret();
            if(rets.size() == 1)
                return rets.getChild(0).getType();
            else
                return rets.getType();
        }
    }

    public boolean typeEquals(BasicType other) {
        if(!(other instanceof Function fother))
            return false;
        return args.typeEquals(fother.getArgs()) && rets.typeEquals(fother.getRets());
    }

    public boolean equals(Object other) {
        return other instanceof BasicType bother &&
                typeEquals(bother) &&
                bother.getName().equals(getName());
    }

    @Override
    public String toString() {
        return super.toString() + args + rets;
    }
}
