package type.numerical;


import compiler.Assembly;
import compiler.commands.Move;
import compiler.commands.Pop;
import compiler.commands.Push;
import compiler.commands.arithmetic.AddInt;
import compiler.commands.arithmetic.ArithmeticCommand;
import compiler.components.Register;
import type.Tuple;
import type.Type;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Numerical extends Type {
    public double asDouble(){
        if (this instanceof Float f) {
            return f.getValue();
        } else if (this instanceof Int i) {
            return i.getValue();
        } else if (this instanceof Char c) {
            return c.getValue();
        } else {
            throw new Error(errorString("can not get double value of " + this));
        }
    }
    public int asInt(){
        if (this instanceof Float f) {
            return (int) f.getValue();
        } else if (this instanceof Int i) {
            return i.getValue();
        } else if (this instanceof Char c) {
            return c.getValue();
        } else{
            throw new Error(errorString("can not get int value of " + this));
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


    public abstract Int getByteSize();
}
