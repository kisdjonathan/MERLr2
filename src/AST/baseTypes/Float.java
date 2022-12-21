package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.abstractNode.SyntaxNode;
import interpreter.Context;
import interpreter.Value;

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

    public boolean typeEquals(SyntaxNode other) {
        return false; //TODO
    }
    public boolean typeContains(SyntaxNode other) {
        return false; //TODO
    }

    protected int defaultByteSize() {
        return 4;
    }

    public String toString() {
        return super.toString() + " " + value;
    }

    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public Value interpret(Context context) {
        return null;
    }
}
