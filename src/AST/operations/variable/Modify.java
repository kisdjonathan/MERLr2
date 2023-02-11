package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Tuple;
import AST.baseTypes.flagTypes.InternalMessage;
import AST.components.Locality;
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

    public Modify clone() {
        Modify ret = new Modify();
        for(SyntaxNode child : getChildren())
            ret.addChild(child.clone());
        return ret;
    }

    public BasicType getType() {
        return getChild(size() - 1).getType();
    }


    private static SyntaxNode typedVariable(SyntaxNode potentialvar, SyntaxNode potentialval, Locality variables) {
        //take care of variable-to-variable assignments
        if(potentialvar instanceof Variable var) {
            BasicType resultType = potentialval.getType();
            if (variables.hasVariable(var.getName())) {
                Variable existing = variables.getVariable(var.getName());
                if(existing.getType() instanceof InferredType)
                    existing.setType(resultType);
                else if(!existing.getType().typeEquals(resultType) && !resultType.typeEquals(existing.getType()))
                    throw new Error("Modifying variable " + existing + " with incompatible type " + resultType);
                else if(existing.isConstant())
                    throw new Error("Modifying constant " + existing + " to " + potentialval);
                return existing;
            }
            else {
                var.setType(resultType);
                variables.putVariable(var.getName(), var);
            }
        }
        else if (potentialvar instanceof Tuple multivar){
            if(potentialval instanceof Tuple multival) {
                //TODO tuples with skipped elements ie (a,b,...,c) = (1,2,3,4,5,6)
                int j = 0, k = 0;   //j:lhs, k:rhs
                while (j < multivar.size() && k < multival.size()) {
                    multivar.setChild(j, typedVariable(multivar.getChild(j), multival.getChild(k), variables));
                    ++j; ++k;
                }
                return multivar;
            }
            else {
                potentialval.setType(multivar);
                return multivar;
            }
        }
        potentialvar.setType(potentialval.getType());
        return potentialvar;
    }

    public void unifyVariables(Locality variables) {
        //take care of variable-to-variable assignments
        SyntaxNode val = getChild(size() - 1);
        if(val instanceof Variable var){
            if(variables.hasVariable(var.getName())) {
                val = var = variables.getVariable(var.getName());
                setChild(size() - 1, var);
            }
            else
                throw new Error("Using undefined variable " + val);
        }
        else
            val.unifyVariables(variables);

        //Match types between lhs and rhs
        for(int i = size() - 2; i >= 0; --i) {
            setChild(i, typedVariable(getChild(i), val, variables));
        }
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    private BasicType interpretPair(SyntaxNode a, SyntaxNode b) {
        if(a instanceof Variable var) {
            var.setType(b.interpret());
            return var.interpret();
        }
        else if(a instanceof Tuple multivar && b instanceof Tuple multival) {
            int j = 0, k = 0;
            BasicType multivalresp = multival.interpret();
            if(multivalresp instanceof InternalMessage)
                return multivalresp;
            multival = (Tuple) multivalresp;
            while(j < multivar.size() && k < multival.size()) {
                multivar.getChild(j).setType(interpretPair(multivar.getChild(j), multival.getChild(k)));
                ++j; ++k;
            }
            return multivar.interpret();
        }
        throw new Error("attempting to assign " + b + " to " + a);
    }

    public BasicType interpret() {
        BasicType value = getChild(size() - 1).interpret();
        for(int i = 0; i < size()-1; ++i) {
            if(getChild(i) instanceof Tuple multi) {
                int j = 0, k = 0;
                while(j < multi.size() && k < value.size()){
                    interpretPair(multi.getChild(j), value.getChild(k));
                    ++j; ++k;
                }
            }
            else
                getChild(i).setType(value); //TODO non-variable assign
        }
        return value;
    }
}
