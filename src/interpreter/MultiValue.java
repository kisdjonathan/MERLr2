package interpreter;


import type.Tuple;
import type.Type;

import java.util.ArrayList;
import java.util.List;

//Equivalent to tuple in values
public class MultiValue implements Value {
    private final List<Value> values = new ArrayList<>();

    public MultiValue(){}
    public MultiValue(List<Value> values){
        this.values.addAll(values);
    }

    public void addComponent(Value value) {
        values.add(value);
    }
    public Value getComponent(int at) {
        return values.get(at);
    }

    public Tuple getValue() {
        Tuple ret = new Tuple();
        for(Value value : values)
            ret.addChild(value.getValue());
        return ret;
    }
    public Type getValue(int at) {
        return values.get(at).getValue();
    }

    public void setValue(Type value) {
        if(value.size() != values.size())
            throw new Error("Runtime Error: assignment dimensions do not match between " + values + " and " + value);

        for(int i = 0; i < values.size(); ++i) {
            values.get(i).setValue((Type) value.getChild(i));
        }
    }

    public void addValue(Value value) {
        values.add(value);
    }
}