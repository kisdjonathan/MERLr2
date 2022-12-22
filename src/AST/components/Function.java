package AST.components;

import AST.baseTypes.BasicType;
import AST.baseTypes.Signature;

public class Function extends Variable{
    public Function(String name) {
        super(name);
    }

    public Signature getType() {
        return null;    //TODO
    }
}
