package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.components.Signature;
import AST.components.Variable;
import AST.operations.Operator;

import java.util.Map;

//TODO
public class Modify extends Operator {
    public Modify(){}
    public Modify(SyntaxNode dest, SyntaxNode value){
        addChild(dest); addChild(value);
    }

    public String getName() {
        return "modify";
    }

    public Declare clone() {
        Declare ret = new Declare();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }

    public BasicType getType() {
        return getChild(size() - 1).getType();
    }

    public void unifyVariables(Map<String, Variable> variables) {
        SyntaxNode val = getChild(size() - 1);
        val.unifyVariables(variables);
        BasicType resultType = val.getType();
        for(int i = size() - 2; i >= 0; --i) {
            if(getChild(i) instanceof Variable var) {
                if (variables.containsKey(var.getName())) {
                    Variable existing = variables.get(var.getName());
                    if(!existing.getType().typeEquals(resultType))
                        throw new Error("Assigning to variable " + existing + " with incompatible type " + resultType);
                    setChild(i, existing);
                }
                else {
                    var.setType(resultType);
                    variables.put(var.getName(), var);
                }
            }
            else {
                getChild(i).setType(resultType);
            }
        }
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    public BasicType interpret() {
        BasicType value = getChild(size() - 1).interpret();
        for(int i = 0; i < size()-1; ++i)
            getChild(i).setType(value);
        return value;
    }
}
