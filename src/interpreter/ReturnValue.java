package interpreter;

import type.Type;
import type.VoidType;

public class ReturnValue implements Value{
    private Type value = new VoidType();
    public Type getValue() {
        return value;
    }
    public void setValue(Type value) {
        throw new Error("Runtime Error: attempting to assign to return statement");
    }

    public ReturnValue(Type value) {
        this.value = value;
    }
}