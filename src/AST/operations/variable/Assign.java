package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.components.Function;
import AST.components.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

import java.util.Map;

public class Assign extends Operator {
    public Assign(){}
    public Assign(SyntaxNode dest, SyntaxNode value){
        addChild(dest); addChild(value);
    }

    public String getName() {
        return "assign";
    }

    public BasicType getType() {
        return getChild(size() - 1).getType();
    }

    public void unifyVariables(Map<String, Variable> variables) {
        getChild(size() - 1).unifyVariables(variables);
        BasicType type = getChild(size() - 1).getType();
        for(int i = 0; i < size()-1; ++i) {
            if(getChild(i) instanceof Variable var && !variables.containsKey(var.getName())) {
                var.setType(type);
                variables.put(var.getName(), var);
            }
            else
                getChild(i).unifyVariables(variables);
        }
    }

    public BasicType interpret() {
        BasicType value = getChild(size() - 1).interpret();
        for(int i = 0; i < size()-1; ++i)
            getChild(i).setType(value);
        return value;
    }
}
