package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.variables.Variable;
import AST.variables.VariableEntry;

import java.util.HashMap;
import java.util.Map;

//TODO complete
public abstract class BasicType extends SyntaxNode {
    /**
     * returns the name of the type
     * comparing names will not suffice to check for equality
     **/
    public abstract String getName();

    /**
     * implements the getUsage of SyntaxNode
     */
    public BasicType getType(){
        return this;
    }

    public BasicType interpret() {
        return this;
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
     * shorthand for adding fields to a variable and returning the variable
     */
    public BasicType acceptFields(Map<String, VariableEntry> fields) {
        this.fields.putAll(fields);
        return this;
    }

    /**
     * @return true if this can be converted to obj inherently
     */
    public abstract boolean typeEquals(BasicType obj);


    public abstract BasicType clone();

    //TODO heap and stack allocation functions/fields

    public String valueString() {
        return toString();
    }
    public String toString() {
        return getName() + (fields.size() == 0 ? "" : fields.toString());
    }
}
