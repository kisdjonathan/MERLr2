package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.components.Signature;
import AST.components.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

import java.util.Map;

public class Declare extends Operator {
    private Function functionDeclaration = null;

    public Declare(){}
    public Declare(SyntaxNode dest, SyntaxNode value){
        addChild(dest); addChild(value);
    }

    public String getName() {
        return "declare";
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
        //TODO function
        for(int i = size() - 2; i >= 0; --i) {
            if (getChild(i) instanceof Call call) {
                Function f = call.createFunction(getChild(size() - 1));
                Signature sig = call.asVariable();
                if(!variables.containsKey(sig.getName()) || !(variables.get(sig.getName()) instanceof Signature))
                    variables.put(sig.getName(), sig);
                else
                    sig = (Signature) variables.get(sig.getName());

                f.unifyVariables(variables);
                sig.addOverload(f);
                functionDeclaration = f;
            }
            else {
                Variable var = getChild(i).asVariable();
                if(variables.containsKey(var.getName()))
                    throw new Error("attempting to perform constant assignment on defined " + variables.get(var.getName()));
                SyntaxNode val = getChild(size() - 1);
                val.unifyVariables(variables);
                var.setType(val.getType());
                var.setConstant(true);
                variables.put(var.getName(), var);
            }
        }
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    public BasicType interpret() {
        if(functionDeclaration != null)
            return functionDeclaration;
        BasicType value = getChild(size() - 1).interpret();
        for(int i = 0; i < size()-1; ++i) {
            if (!getChild(i).asVariable().isConstant())
                throw new Error("attempting to assign variable " + getChild(i) + " to a constant value");
            else if(!getChild(i).getType().equals(value))
                throw new Error("attempting to assign constant " + getChild(i) + " to a new value " + value);
            else
                getChild(i).setType(value);
        }
        return value;
    }
}
