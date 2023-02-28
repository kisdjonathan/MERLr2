package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Structure;
import AST.baseTypes.Tuple;
import AST.baseTypes.advanced.Container;
import AST.baseTypes.advanced.Storage;
import AST.baseTypes.flagTypes.InternalMessage;
import AST.components.Locality;
import AST.components.Variable;
import AST.operations.Operator;

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


    private static SyntaxNode matchTypes(SyntaxNode potentialvar, SyntaxNode potentialval, Locality variables) {
        //take care of variable-to-variable assignments
        if(potentialvar instanceof Variable var) {
            BasicType resultType = potentialval.getType();
            if (variables.hasVariable(var.getName())) {
                Variable existing = variables.getVariable(var.getName());
                if(existing.getType() instanceof InferredType)
                    existing.setType(resultType.clone());
                else if(resultType instanceof InferredType)
                    potentialval.setType(existing.getType());
                else if(!existing.getType().typeEquals(resultType) && !resultType.typeEquals(existing.getType()))
                    throw new Error("Modifying variable " + existing + " with incompatible type " + resultType);
                else if(existing.isConstant())
                    throw new Error("Modifying constant " + existing + " to " + potentialval);
                return existing;
            }
            else {
                var.setType(resultType.clone());
                variables.putVariable(var.getName(), var);
                return var;
            }
        }
        else if (potentialvar instanceof Tuple multivar){
            if(potentialval.getType() instanceof Tuple multival) {
                //TODO tuples with skipped elements ie (a,b,...,c) = (1,2,3,4,5,6)
                int j = 0, k = 0;   //j:lhs, k:rhs
                while (j < multivar.size() && k < multival.size()) {
                    multivar.setChild(j, matchTypes(multivar.getChild(j), multival.getChild(k), variables));
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
                val = variables.getVariable(var.getName());
                setChild(size() - 1, val);
            }
            else
                throw new Error("Using undefined variable " + val);
        }
        else
            val.unifyVariables(variables);

        //Match types between lhs and rhs
        for(int i = size() - 2; i >= 0; --i) {
            setChild(i, matchTypes(getChild(i), val, variables));
        }
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    private BasicType interpretPair(SyntaxNode a, SyntaxNode b) {
        if(a instanceof Variable var) {
            if(!(a.getType() instanceof Container) && //TODO not a complete fix for Container. Should allow inter-container assign
                    a.getType() instanceof Structure struct1 && b.getType() instanceof Structure struct2) {
                for(Variable field : struct2.getVariables().values())
                    struct1.getVariable(field.getName()).setType(field.getType().clone());
            }
            else
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
            interpretPair(getChild(i), value);
//            if(getChild(i) instanceof Tuple multi) {
//                int j = 0, k = 0;
//                while(j < multi.size() && k < value.size()){
//                    interpretPair(multi.getChild(j), value.getChild(k));
//                    ++j; ++k;
//                }
//            }
//            else if(getChild(i) instanceof Variable var) {
//                if(getChild(i).getType() instanceof Structure struct && value instanceof Structure structValue) {
//                    for(Variable field : struct.getVariables().values())
//                        field.setType(structValue.getVariable(field.getName()).getType().clone());
//                }
//                else
//                    var.setType(value.interpret());
//                return var.interpret();
//            }
//            else
//                getChild(i).setType(value); //TODO non-variable assign
        }
        return value;
    }
}
