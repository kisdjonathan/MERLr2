package interpreter;

import type.Type;

public class RawValue implements Value{
    private Type value;

    public RawValue(Type value) {
        this.value = value;
    }

    public Type getValue(){
        return value;
    }

    public void setValue(Type value) {
        throw new Error("Runtime Error: setting value of non-assignable");
    }
}
