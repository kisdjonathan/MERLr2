package type;

import compiler.Assembly;

public class VoidType extends Type {
    public String getName() {
        return "void";
    }

    public Assembly getAssembly() {
        return new Assembly();  //TODO
    }


    public boolean typeEquals(Type other) {
        return true;
    }
    public VoidType emptyClone() {
        return new VoidType();
    }
}
