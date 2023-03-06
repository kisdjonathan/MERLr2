package AST.baseTypes.numerical;

import AST.baseTypes.BasicType;

public class Bool extends BasicType {
    private boolean value = false;

    public Bool(){}
    public Bool(boolean val) {
        value = val;
    }

    public String getName() {
        return "bool";
    }

    public Bool clone() {
        return (Bool)(new Bool(value).acceptFields(getFieldClones()));
    }

    public boolean getValue(){
        return value;
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Bool;
    }

    public String valueString() {
        return String.valueOf(value);
    }

    public String toString() {
        return super.toString() + " " + value;
    }

    public boolean equals(Object other) {
        return other instanceof Bool b && b.value == value;
    }
}
