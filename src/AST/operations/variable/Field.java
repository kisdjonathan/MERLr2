package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.components.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//AST.Contextualization simply makes the fields of a variable available to its children
//TODO complete
public class Field extends Operator {
    private Map<String, Variable> loadedVariables = new HashMap<>();
    private Map<String, List<Function>> loadedFunctions = new HashMap<>();

    public Field() {
    }
    public Field(SyntaxNode context, SyntaxNode body) {
        addChild("field", context);
        addChild(null, body);
    }

    public Variable getVariable(String name) {
        if(loadedVariables.containsKey(name))
            return loadedVariables.get(name);
        return super.getVariable(name);
    }

    public String getName() {
        return "field";
    }

    public Field clone() {
        return new Field(getChild(0).clone(), getChild(1).clone());
    }

    //TODO
    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
