package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.flagTypes.ReturnCode;
import AST.components.Locality;
import AST.components.Variable;

import java.util.HashMap;
import java.util.Map;

public class Function extends BasicType implements Locality {
    private Tuple args = null, rets = null;
    private final Map<String, Variable> variables = new HashMap<>();

    public Function() {}
    public Function(Tuple args, Tuple rets) {
        this.args = args;
        this.rets = rets;
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
    }

    public Tuple getRets() {
        return rets;
    }
    public void setRets(SyntaxNode second) {
        rets = Tuple.asTuple(second);
        rets.setParent(this);
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public Function clone() {
        Function ret = new Function(args.clone(), rets.clone());
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        ret.unifyVariables(new Locality.Wrapper(getVariableClones()));
        return ret;
    }

    public void unifyVariables(Locality variables) {
        Locality.Layer localLayer = new Locality.Layer(variables);

        for(SyntaxNode arg : args) {
            Variable var = arg.asVariable();
            localLayer.putVariable(var.getName(), var);
        }
        for(SyntaxNode ret : rets) {
            if(ret instanceof BasicType)
                break;
            Variable var = ret.asVariable();
            localLayer.putVariable(var.getName(), var);
        }
        super.unifyVariables(localLayer);
        getVariables().putAll(localLayer.getVariables());
    }

    public BasicType interpretExecute(Tuple args) {
        //reset original conditions
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
            BasicType val = getChild(0).interpret();
            if(val instanceof ReturnCode rval)
                return rval.getValue();
            else if(rets.size() == 1)
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
