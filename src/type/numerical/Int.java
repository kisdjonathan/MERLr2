package type.numerical;

import AST.abstractNode.SyntaxNode;
import compiler.Assembly;
import type.Type;

public class Int extends Numerical {
    private int value = 0;

    public Int(){}
    public Int(int val){
        value = val;
    }
    public Int(String val) {
        try {
            value = Integer.parseInt(val);
        }
        catch (NumberFormatException notInt) {
            value = (int) Double.parseDouble(val);
        }
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


    public static int getDefaultByteSize() {
        return 4;
    }
    public Int getByteSize() {
        return new Int(isLong() ? 8 : 4);
    }


    public Int emptyClone() {
        return new Int();
    }
    public Int clone() {
        return new Int(value);
    }

    public boolean typeEquals(Type other) {
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
