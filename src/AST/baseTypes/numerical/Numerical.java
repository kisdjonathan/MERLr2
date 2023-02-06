package AST.baseTypes.numerical;

import AST.baseTypes.BasicType;

public abstract class Numerical extends BasicType {
    public double asDouble(){
        if (this instanceof Float f) {
            return f.getValue();
        } else if (this instanceof Int i) {
            return i.getValue();
        } else {
            //TODO
            return 0.0;
        }
    }
    public int asInt(){
        if (this instanceof Float f) {
            return (int) f.getValue();
        } else if (this instanceof Int i) {
            return i.getValue();
        } else {
            //TODO
            return 0;
        }
    }

    private boolean extended = false;
    private boolean unsigned = false;

    public boolean isLong() {
        return extended;
    }
    public Numerical setLong(boolean v) {
        extended = v;
        return this;
    }

    public boolean isUnsigned() {
        return unsigned;
    }
    public Numerical setUnsigned(boolean v) {
        unsigned = v;
        return this;
    }

    public boolean isNumeric() {
        return true;
    }
}
