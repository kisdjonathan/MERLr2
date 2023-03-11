package AST.variables;

import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.InferredType;
import AST.baseTypes.Tuple;

import java.util.ArrayList;
import java.util.List;

public class VariableEntry {
    private BasicType type = new InferredType();
    private BasicType value;

    public VariableEntry() {}
    public VariableEntry(BasicType type) {
        this.type = type;
    }

    public BasicType getType() {
        return type;
    }
    public void setType(BasicType t) {
        type = t;
    }

    private boolean constant = false;
    public boolean isConstant() {
        return constant;
    }
    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public Variable asVariable(String name) {
        return new Variable(name, this);
    }


    /**
     * interpreter use
     */
    public BasicType getValue() {
        if(value == null)
            return type.clone();
        return value;
    }
    public void setValue(BasicType t) {
        value = t;
    }



    /*Below are methods that are used if this stores a function*/
    private final List<Function> overloads = new ArrayList<>();
    public void addOverload(Function f) {
        getOverloads().add(f);
    }
    public boolean hasOverload(Tuple args, Tuple rets) {
        Function type = new Function(args, rets);
        for(int i = getOverloads().size()-1; i >= 0; --i) {
            if(getOverloads().get(i).typeEquals(type))
                return true;
        }
        return false;
    }
    public boolean hasOverload(){
        return !getOverloads().isEmpty();
    }
    public int overloadsSize(){
        return getOverloads().size();
    }
    public Function getOverload(Tuple args, Tuple rets) {
        Function type = new Function(args, rets);
        for(int i = getOverloads().size()-1; i >= 0; --i) {
            if(getOverloads().get(i).typeEquals(type))
                return getOverloads().get(i);
        }
        throw new Error("no function matching: " + type);
    }
    public List<Function> getOverloads() {
        return overloads;
    }


    /**
     * used for reference variables
     */
    public void setReference(VariableEntry reference) {
        //throw new Error("can not set reference " + reference + " at " + this);
    }



    public VariableEntry clone() {
        VariableEntry ret = new VariableEntry(type.clone());
        ret.setValue(getValue().clone());
        for(Function f : overloads)
            ret.addOverload(f);
        return ret;
    }

    public String toString() {
        return "variable:" + getValue();
    }
}
