package AST.baseTypes.numerical;


import AST.baseTypes.BasicType;

public class Int extends Numerical {
    private int value;

    public Int(){
        value = 0;
    }
    public Int(int val){
        value = val;
    }
    public Int(String val) {
        //TODO val is float 0.0, or int 0,1
        value = Integer.parseInt(val);
    }

    public String getName() {
        return "int";
    }
    public int getValue() {
        return value;
    }

    public void setValue(int v) {
        value = v;
    }

    public Int clone() {
        return (Int)(new Int(value).acceptFields(getFieldClones()));
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Int;
    }
    public boolean equals(Object other) {
        return other instanceof Int iother && iother.value == value;
    }
    public String valueString() {
        return String.valueOf(value);
    }
    public String toString() {
        return super.toString() + " " + value;
    }
}
