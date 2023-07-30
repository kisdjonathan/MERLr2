package type;

import AST.abstractNode.SyntaxNode;
import type.numerical.Int;

public class Str extends Longable {
    private String value = "";

    public Str(){}
    public Str(String val){
        value = val;
    }

    public String getName() {
        return "str";
    }
    public String getValue() {
        return value;
    }
    public void setValue(String v) {
        value = v;
    }


    public static int getDefaultByteSize() {
        return 4;
    }
    public Int getByteSize() {
        return new Int(isLong() ? 8 : 4);
    }


    public Str emptyClone() {
        return new Str();
    }
    public Str clone() {
        return new Str(value);
    }

    public boolean typeEquals(Type other) {
        return other instanceof Str;
    }
    public boolean equals(Object other) {
        return other instanceof Str iother && value.equals(iother.value);
    }

    public String valueString() {
        return value;
    }
    public String toString() {
        return super.toString() + " " + value;
    }
}
