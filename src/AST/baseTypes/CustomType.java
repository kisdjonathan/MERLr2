package AST.baseTypes;

import AST.variables.Variable;
import AST.variables.VariableEntry;

import java.util.Map;

public class CustomType extends BasicType {

    public CustomType(){}
    public CustomType(Map<String, VariableEntry> fields) {
        getFields().putAll(fields);
    }

    //unifyVariables handled in Construct

    public String getName() {
        return "struct";
    }

    public boolean typeEquals(BasicType other) {
        if(other instanceof CustomType sother) {
            for(String varName : getFields().keySet())
                if(!getField(varName).getType().typeEquals(sother.getField(varName).getType()))
                    return false;
            return true;
        }
        return false;
    }
    public CustomType clone() {
        return new CustomType(getFieldClones());
    }

    public String toString() {
        return getFields().toString();
    }
}
