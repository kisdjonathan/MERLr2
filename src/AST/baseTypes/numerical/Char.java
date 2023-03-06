package AST.baseTypes.numerical;

import AST.baseTypes.BasicType;

public class Char extends Numerical {
    private short value = '\0';

    public Char(){}
    public Char(short val) {
        value = val;
    }
    public Char(String val){
        //TODO val is string literal a,b... or 0,1...
        value = (short)val.charAt(0);
    }

    public String getName() {
        return "char";
    }
    public short getValue() {
        return value;
    }

    public Char clone() {
        return (Char)(new Char(value).acceptFields(getFieldClones()));
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Char;
    }
    public String valueString() {
        return String.valueOf(value);
    }
    public String toString() {
        return super.toString() + " " + value;
    }
}
