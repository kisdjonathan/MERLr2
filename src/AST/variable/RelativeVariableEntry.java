package AST.variable;

import interpreter.Value;
import type.Function;
import type.Type;

import java.util.List;

/**
 * pseudo-variable
 * used in structures
 * keeps variable anchored to the same value
 *
 * note: do not use RelativeVariableEntry to store fields of types, use VariableEntry instead
 * only use RelativeVariableEntry to access those fields outside of Type
 */
public class RelativeVariableEntry extends VariableEntry {
    private VariableEntry parent;
    public RelativeVariableEntry(String name, VariableEntry structParent) {
        super(name);
        this.parent = structParent;
    }

    public Type getType() {
        return parent.getType().getField(getName()).getType();
    }
    public void setType(Type t) {
        parent.getType().getField(getName()).setType(t);
    }

    public void setValue(Value value) {
        parent.getValue().getValue().getField(getName()).setValue(value);
    }
    public Value getValue() {
        return parent.getValue().getValue().getField(getName()).getValue();
    }


    /*Below are methods that are used if this stores a function*/
    public List<Function> getOverloads() {
        return parent.getType().getField(getName()).getOverloads();
    }


    public void setReference(VariableEntry reference) {
        parent = reference;
    }

    public RelativeVariableEntry clone(){
        RelativeVariableEntry ret = new RelativeVariableEntry(getName(), parent);
        return ret;
    }
}
