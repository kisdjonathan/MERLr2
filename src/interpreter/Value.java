package interpreter;

import type.Type;

//TODO
public interface Value {
    Type getValue();

    void setValue(Type value);
}
