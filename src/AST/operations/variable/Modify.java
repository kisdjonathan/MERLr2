package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Tuple;
import AST.baseTypes.flagTypes.InternalMessage;
import AST.components.Locality;
import AST.variables.Variable;
import AST.operations.Operator;
import AST.variables.VariableEntry;

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


    /**
     * part of unifyVariables
     */
    private static SyntaxNode matchTypes(SyntaxNode potentialvar, SyntaxNode potentialval, Locality variables) {
        //take care of variable-to-variable assignments
        if(potentialvar instanceof Variable var) {
            var.unifyVariables(variables);
            VariableEntry existing = var.getEntry();
            if(existing.isConstant())
                throw new Error("Modifying constant " + existing + " to " + potentialval);

            BasicType resultType = potentialval.getType();
            var.setType(resultType.clone());

            return var;
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
                potentialvar.unifyVariables(variables);
                return multivar;
            }
        }
        potentialvar.unifyVariables(variables);
        potentialvar.setType(potentialval.getType());
        return potentialvar;
    }

    public void unifyVariables(Locality variables) {
        //take care of variable-to-variable assignments
        SyntaxNode val = getChild(size() - 1);
        val.unifyVariables(variables);

        //Match types between lhs and rhs
        for(int i = size() - 2; i >= 0; --i) {
            setChild(i, matchTypes(getChild(i), val, variables));
        }
    }

    public Variable asVariable() {
        return getChild(0).asVariable();
    }

    private BasicType interpretPair(SyntaxNode a, BasicType b) {
        if(a.isVariable()) {
            Variable var = a.asVariable();
            BasicType overlayValue = b;
            BasicType baseValue = var.getEntry().getValue();

            for(String field : baseValue.getFields().keySet())
                if(!overlayValue.hasField(field))
                    overlayValue.putField(field, baseValue.getField(field).clone());

            var.getEntry().setValue(overlayValue);
            return overlayValue;
        }
        else if(a instanceof Tuple multivar && b instanceof Tuple multival) {
            int j = 0, k = 0;
            BasicType multivalresp = multival.interpret();
            if(multivalresp instanceof InternalMessage)
                return multivalresp;
            multival = (Tuple) multivalresp;
            while(j < multivar.size() && k < multival.size()) {
                interpretPair(multivar.getChild(j), (BasicType)multival.getChild(k));
                ++j; ++k;
            }
            return multival;
        }
        throw new Error("attempting to assign " + b + " to " + a);
    }

    public BasicType interpret() {
        BasicType value = getChild(size() - 1).interpret();
        for(int i = 0; i < size()-1; ++i) {
            interpretPair(getChild(i), value);
        }
        return value;
    }
}
