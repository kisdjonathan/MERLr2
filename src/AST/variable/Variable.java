package AST.variable;

import AST.abstractNode.SyntaxNode;
import interpreter.RawValue;
import interpreter.ReferenceValue;
import interpreter.Value;
import type.InferredType;
import type.Type;

public class Variable extends SyntaxNode {
    private boolean firstOccurrence = true;
    private VariableEntry body;

    public Variable(String name) {
        body = new VariableEntry(name);
    }
    public Variable(String name, Type type) {
        body = new VariableEntry(name, type);
    }
    public Variable(VariableEntry body) {
        this.body = body;
    }

    public String getName() {
        return body.getName();
    }
    public Type getType() {
        return body.getType();
    }
    public void setType(Type type) {
        body.setType(type);
    }

    public VariableEntry getEntry(){
        return body;
    }
    public void setEntry(VariableEntry body){
        this.body = body;
    }

    public boolean isFirstOccurrence() {
        return firstOccurrence;
    }
    public void setFirstOccurrence(boolean firstOccurrence) {
        this.firstOccurrence = firstOccurrence;
    }

    public SyntaxNode emptyClone() {
        return new Variable(getName());
    }
    public void init(SyntaxNode other) {
        Variable vother = (Variable) other;
        vother.setEntry(getEntry().clone());
        vother.firstOccurrence = firstOccurrence;
    }

    public Value interpret() {
        if(isFirstOccurrence())
            body.setValue(new RawValue(body.getType()));
        return new ReferenceValue(body);
    }
}
