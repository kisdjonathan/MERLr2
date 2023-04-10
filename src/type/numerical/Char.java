package type.numerical;

import type.Type;

public class Char extends Numerical{
    private char value = '\0';

    public Char(){}
    public Char(char val) {
        value = val;
    }

    public String getName() {
        return "char";
    }
    public char getValue() {
        return value;
    }

    public Char emptyClone() {
        return new Char();
    }
    public Char clone() {
        return new Char(value);
    }


    public Int getByteSize() {
        return new Int(isLong() ? 2 : 1);
    }


    public boolean typeEquals(Type other) {
        return other instanceof Char;
    }
    public boolean equals(Object other) {
        return other instanceof Char cother && cother.value == value;
    }

    public String valueString() {
        return String.valueOf(value);
    }
    public String toString() {
        return super.toString() + " " + value;
    }
}
