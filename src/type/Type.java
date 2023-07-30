package type;

import AST.abstractNode.SyntaxNode;
import AST.variable.VariableEntry;
import compiler.Assembly;
import interpreter.RawValue;
import interpreter.ReferenceValue;
import interpreter.Value;
import type.numerical.Int;

import java.util.HashMap;
import java.util.Map;

public abstract class Type extends SyntaxNode {
    /**
     * both are the same
     */
    public static boolean typeEquals(Type first, Type second) {
        return (first.isInferred() || second.isInferred()) ||
                (first.typeEquals(second) || second.typeEquals(first));
    }

    /**
     * returns the name of the type
     * comparing names will not suffice to check for equality
     **/
    public abstract String getName();
    public Type getType(){
        return this;
    }


    /**
     * true if the type is currently undefined to some extent
     * used with InferredType
     */
    public boolean isInferred() {
        return false;
    }


    /**
     * the state of being a constant variable is stored by the type
     */
    private boolean constant = false;
    public boolean isConstant() {
        return constant;
    }
    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    /**
     * every value has fields stored as part of its type,
     * which is here in fields
     */
    private final Map<String, VariableEntry> fields = new HashMap<>();
    /**
     * returns all fields of this
     **/
    public Map<String, VariableEntry> getFields() {
        return fields;
    }
    /**
     * returns the variable associated with field name
     * if none exists, returns null
     * used by compiler
     **/
    public VariableEntry getField(String name) {
        return fields.get(name);
    }
    /**
     * sets the entry for name to var
     * used by compiler
     **/
    public void putField(String name, VariableEntry var) {
        fields.put(name, var);
    }
    /**
     * sets the entry for name to var for all pairs of entry and var in vars
     * used by compiler
     **/
    public void putFields(Map<String, VariableEntry> vars) {
        fields.putAll(vars);
    }
    /**
     * returns true if there is a field by the key name
     * used by compiler
     */
    public boolean hasField(String name) {
        return fields.containsKey(name);
    }

    /**
     * creates a clone of every variable
     * used for inheritance
     */
    public Map<String, VariableEntry> getFieldClones() {
        Map<String, VariableEntry> ret = new HashMap<>();
        for(String varName : getFields().keySet()) {
            ret.put(varName, getFields().get(varName).clone());
        }
        return ret;
    }


    /**
     * true if this is obj
     * ie. true if this is inferred and other is any
     * ie. true if this is sequence and other is tuple
     * ie. false if this is tuple and other is sequence
     */
    public abstract boolean typeEquals(Type obj);



    public String valueString() {
        return toString();
    }
    public String toString() {
        return getName();
    }

    public Value interpret() {
        return new RawValue(this);
    }


    public SyntaxNode getByteSize(){
        throw new Error(errorString("undetermined size for " + this));
    }
    public Int getIntByteSize(){
        try {
            return (Int) getByteSize();
        }
        catch (Exception e) {
            throw new Error(errorString("a fixed size is required but instead received " + this));
        }
    }

    public void compile(Assembly body) {}
}