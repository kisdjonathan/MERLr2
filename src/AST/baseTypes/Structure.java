package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.components.Locality;
import AST.components.Variable;

import java.util.HashMap;
import java.util.Map;

public class Structure extends BasicType implements Locality {
    private final Map<String, Variable> fields = new HashMap<>();

    public Map<String, Variable> getVariables() {
        return fields;
    }


    public Structure(){}
    public Structure(Map<String, Variable> fields) {
        this.fields.putAll(fields);
    }

    //unifyVariables handled in Construct

    public String getName() {
        return "struct";
    }

    public boolean typeEquals(BasicType other) {
        if(other instanceof Structure sother) {
            for(String varName : fields.keySet())
                if(!getVariable(varName).getType().typeEquals(sother.getVariable(varName).getType()))
                    return false;
            return true;
        }
        return false;
    }
    public Structure clone() {
        return new Structure(getVariableClones());
    }

    public String toString() {
        return fields.toString();
    }
}
