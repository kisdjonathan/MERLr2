package AST.baseTypes.numerical;

import AST.baseTypes.BasicType;

public class Float extends Numerical {
    private double value = 0;

    public static Float ZERO = new Float(0.0);

    public Float(){}
    public Float(double val) {
        value = val;
    }
    public Float(String val) {
        value = Double.parseDouble(val);
    }

    public String getName() {
        return "float";
    }
    public double getValue() {
        return value;
    }

    public void setValue(double v){
        value = v;
    }

    public Float clone() {
        return (Float)(new Float(value).acceptFields(getFieldClones()));
    }

    public String valueString() {
        return String.valueOf(value);
    }
    public String toString() {
        return super.toString() + " " + value;
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Float;
    }

    public boolean equals(Object other) {
        if(other instanceof  Float f)
            return f.value == value;
        return false;
    }
}
