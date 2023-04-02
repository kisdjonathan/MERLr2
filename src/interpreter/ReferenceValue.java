package interpreter;

import AST.variable.VariableEntry;
import type.Type;

public class ReferenceValue implements Value{
    private VariableEntry entry;

    public ReferenceValue(VariableEntry entry) {
        this.entry = entry;
    }

    public Type getValue() {
        return entry.getValue().getValue();
    }

    public void setValue(Type value) {
        entry.setValue(new RawValue(value));
    }
}
