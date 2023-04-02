package type;

import AST.abstractNode.SyntaxNode;
import AST.variable.Layer;
import AST.variable.Locality;
import AST.variable.Variable;
import AST.variable.VariableEntry;
import compiler.Assembly;
import interpreter.RawValue;
import interpreter.ReturnValue;
import interpreter.Value;

import java.util.HashMap;
import java.util.Map;

public class Function extends Type implements Locality{
    private Tuple args = null, rets = null;
    private final Map<String, VariableEntry> variables = new HashMap<>();
    private Locality parent = null;    //fix for methods that lose access to instance variables when cloned

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
    }

    public Tuple getRets() {
        return rets;
    }
    public void setRets(SyntaxNode second) {
        rets = Tuple.asTuple(second);
    }

    public Map<String, VariableEntry> getVariables() {
        return variables;
    }
    public boolean hasVariable(String name) {
        return variables.containsKey(name) || hasField(name) || parent != null && parent.hasVariable(name);
    }
    public VariableEntry getVariable(String name) {
        return variables.containsKey(name) ? variables.get(name) : hasField(name) ? getField(name) : parent.getVariable(name);
    }

    public void unifyVariables(Locality variables) {
        parent = variables;

        args.unifyVariables(this);
        rets.unifyVariables(this);

        super.unifyVariables(this);
    }

    public SyntaxNode emptyClone() {
        return new Function((Tuple)args.clone(), (Tuple)rets.clone());
    }

    public void init(SyntaxNode other) {
        Function ret = (Function) other;

        ret.getFields().putAll(getFieldClones());
        ret.variables.putAll(getVariableClones());
        ret.unifyVariables(parent);
    }

    public Value interpretExecute(Tuple args) {
        //reset original conditions
        this.args.interpret();
        for(int i = 0; i < args.size(); ++i)
            this.args.getChild(i).asVariable().setType(args.getChild(i).getType());

        if(rets.size() == 0) {
            getChild(0).interpret();
            return new RawValue(new VoidType());
        }
        else if(rets.size() == 1 && rets.getChild(0) instanceof InferredType) {
            Value value = getChild(0).interpret();
            if(value instanceof ReturnValue retVal)
                return new RawValue(retVal.getValue());
            else
                return value;
        }
        else{
            this.rets.interpret();
            Value val = getChild(0).interpret();
            if(val instanceof ReturnValue rval)
                return new RawValue(rval.getValue());
            else if(rets.size() == 1)
                return new RawValue(rets.getChild(0).getType());
            else
                return new RawValue(rets.getType());
        }
    }

    public Assembly getAssembly() {
        return null;
    }

    public boolean typeEquals(Type other) {
        if(!(other instanceof Function fother))
            return false;
        return args.typeEquals(fother.getArgs()) && rets.typeEquals(fother.getRets());
    }

    public boolean equals(Object other) {
        return other instanceof Type bother &&
                typeEquals(bother) &&
                bother.getName().equals(getName());
    }

    public String toString() {
        return super.toString() + args + rets;
    }
}
