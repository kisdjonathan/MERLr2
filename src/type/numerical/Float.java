package type.numerical;

import AST.abstractNode.SyntaxNode;
import type.Type;

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

    public Float emptyClone() {
        return new Float();
    }
    public Float clone() {
        return new Float(value);
    }


    public Int getByteSize() {
        return new Int(isLong() ? 8 : 4);
    }


    public boolean typeEquals(Type other) {
        return other instanceof Float;
    }
    public boolean equals(Object other) {
        return other instanceof Float fother && fother.value == value;
    }

    public String valueString() {
        return String.valueOf(value);
    }
    public String toString() {
        return super.toString() + " " + value;
    }
}
