package AST.variables;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * pseudo-variable
 * used in structures
 * keeps variable anchored to the same value
 */
public class RelativeVariableEntry extends VariableEntry {
    private String name;
    private VariableEntry parent;
    public RelativeVariableEntry(String name, VariableEntry structParent) {
        this.name = name;
        this.parent = structParent;
    }

    public BasicType getType() {
        return parent.getType().getField(name).getType();
    }
    public void setType(BasicType t) {
        VariableEntry thisField =  parent.getType().getField(name);
        thisField.setType(t);
    }

    public void setValue(BasicType value) {
        parent.getValue().getField(name).setValue(value);
    }
    public BasicType getValue() {
        return parent.getValue().getField(name).getValue().clone();
    }


    /*Below are methods that are used if this stores a function*/
    public List<Function> getOverloads() {
        return parent.getType().getField(name).getOverloads();
    }


    public RelativeVariableEntry clone(){
        RelativeVariableEntry ret = new RelativeVariableEntry(name, parent);
        return ret;
    }
}
