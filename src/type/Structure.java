package type;

import AST.abstractNode.SyntaxNode;
import AST.variable.Locality;
import AST.variable.Variable;
import AST.variable.VariableEntry;

import java.util.HashMap;
import java.util.Map;

public class Structure extends Type {

    public Structure(){}
    public Structure(Map<String, VariableEntry> fields) {
        getFields().putAll(fields);
    }

    //unifyVariables handled in Construct

    public String getName() {
        return "struct";
    }

    public boolean typeEquals(Type other) {
        if(other instanceof Structure sother) {
            for(String varName : getFields().keySet())
                if(!getField(varName).getType().typeEquals(sother.getField(varName).getType()))
                    return false;
            return true;
        }
        return false;
    }
    public Structure emptyClone() {
        return new Structure();
    }
    public void init(SyntaxNode original) {
        ((Structure) original).putFields(getFieldClones());
    }

    public String toString() {
        return getFields().toString();
    }
}