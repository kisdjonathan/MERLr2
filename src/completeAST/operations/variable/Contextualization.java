package completeAST.operations.variable;

import baseAST.SyntaxNode;
import derivedAST.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//derivedAST.Contextualization simply makes the fields of a variable available to its children
//TODO complete
public class Contextualization extends operations.BuiltinOperation {
    private Map<String, Variable> loadedVariables = new HashMap<>();
    private Map<String, List<Function>> loadedFunctions = new HashMap<>();

    public Contextualization() {
    }
    public Contextualization(SyntaxNode context, SyntaxNode body) {
        setOrigin(context);
        setVector(body);
    }
    public Contextualization(FinalSyntaxNode context, FinalSyntaxNode body) {
        setOrigin(context);
        setVector(body);
    }

    public void setOrigin(FinalSyntaxNode origin) {
        super.setVector(origin);
        for(FinalSyntaxNode field : origin.getBaseType().getFields()) {
            RelativeVariable ref = new RelativeVariable(field.getName(), field.getDeclaredType());
            ref.setReference(origin);
            loadedVariables.put(field.getName(), ref);
        }
        for(Function field : origin.getBaseType().getMethods()) {
            if(!loadedFunctions.containsKey(field.getName()))
                loadedFunctions.put(field.getName(), new ArrayList<>());
            RelativeFunction ref = new RelativeFunction(field.getName(), field.getDeclaredType());
            ref.setReference(origin);
            loadedFunctions.get(field.getName()).add(ref);
        }
    }

    public void evaluate(){

    }

    public Variable getVariable(String name) {
        if(loadedVariables.containsKey(name))
            return loadedVariables.get(name);
        return super.getVariable(name);
    }

    public List<Function> getFunction(String name) {
        List<Function> ret = super.getFunction(name);
        if(loadedFunctions.containsKey(name))
            ret.addAll(loadedFunctions.get(name));
        return ret;
    }
}
