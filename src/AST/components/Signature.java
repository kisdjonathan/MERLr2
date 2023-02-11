package AST.components;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.Tuple;

import java.util.ArrayList;
import java.util.List;

public class Signature extends Variable {
    private List<Function> overloads = new ArrayList<>();

    public Signature(String name) {
        super(name);
    }

    public void addOverload(Function f) {
        overloads.add(f);
    }

    public boolean hasOverload(Tuple args, Tuple rets) {
        Function type = new Function(args, rets);
        for(int i = overloads.size()-1; i >= 0; --i) {
            if(overloads.get(i).typeEquals(type))
                return true;
        }
        return false;
    }

    public Function getOverload(Tuple args, Tuple rets) {
        Function type = new Function(args, rets);
        for(int i = overloads.size()-1; i >= 0; --i) {
            if(overloads.get(i).typeEquals(type))
                return overloads.get(i);
        }
        throw new Error("no function matching " + getName()  + ": " + type);
    }

    public Signature clone() {
        Signature ret = new Signature(getName());
        for(Function f : overloads)
            ret.addOverload(f); //TODO clone?
        return ret;
    }
}
