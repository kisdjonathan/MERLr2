package AST.variable;

import AST.abstractNode.SyntaxNode;
import interpreter.Value;
import type.Function;
import type.InferredType;
import type.Tuple;
import type.Type;

import java.util.ArrayList;
import java.util.List;

public class VariableEntry {
    private final String name;
    private Type type = new InferredType();
    private Value value;

    public VariableEntry(String name) {
        this.name = name;
    }
    public VariableEntry(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type t) {
        type = t;
    }

    public boolean isConstant() {
        return type.isConstant();
    }
    public void setConstant(boolean constant) {
        type.setConstant(constant);
    }

    public Value getValue() {
        return value;
    }
    public void setValue(Value value) {
        this.value = value;
    }

    public VariableEntry clone() {
        VariableEntry ret = new VariableEntry(name, type);
        ret.setValue(getValue());
        return ret;
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
}
