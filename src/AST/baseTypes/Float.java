package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

public class Float extends Numerical{
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

    public Float clone() {
        return new Float(value);
    }

    public String toString() {
        return super.toString() + " " + value;
    }

    public boolean equals(Object other) {
        if(other instanceof  Float f)
            return f.value == value;
        return false;
    }
}
