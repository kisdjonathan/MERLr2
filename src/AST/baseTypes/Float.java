package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.abstractNode.SyntaxNode;

public class Float extends Numerical{
    private double value = 0;

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

    public boolean typeEquals(SyntaxNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) == 0;
    }
    public boolean typeContains(SyntaxNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) >= 0;
    }

    protected int defaultByteSize() {
        return 4;
    }

    public String toString() {
        return super.toString() + " " + value;
    }
}
