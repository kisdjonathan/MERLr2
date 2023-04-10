package type;

import compiler.Assembly;

public class VoidType extends Type {
    public String getName() {
        return "void";
    }

    public boolean typeEquals(Type other) {
        return true;
    }
    public VoidType emptyClone() {
        return new VoidType();
    }
}
