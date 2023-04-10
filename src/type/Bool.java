package type;

public class Bool extends Type {
    private boolean value = false;

    public Bool(){}
    public Bool(boolean val) {
        value = val;
    }

    public String getName() {
        return "bool";
    }

    public Bool emptyClone() {
        return new Bool();
    }
    public Bool clone() {
        return new Bool(value);
    }

    public boolean getValue(){
        return value;
    }

    public boolean typeEquals(Type other) {
        return other instanceof Bool;
    }
    public boolean equals(Object other) {
        return other instanceof Bool b && b.value == value;
    }


    public String valueString() {
        return String.valueOf(value);
    }
    public String toString() {
        return super.toString() + " " + value;
    }
}
