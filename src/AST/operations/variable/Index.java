package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import AST.baseTypes.advanced.Sequence;
import AST.baseTypes.numerical.Numerical;
import AST.components.Variable;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

//builtin indexing provided for array/list types only
public class Index extends Operator {
    public Index(SyntaxNode ref, SyntaxNode pos) {
        addChild(ref); addChild(pos);
    }

    public String getName() {
        return "index";
    }

    public Index clone() {
        return new Index(getChild(0).clone(), getChild(1).clone());
    }

    @Override
    public BasicType getType() {
        BasicType parentType = getChild(0).getType();
        if(parentType instanceof Tuple tupleType) {
            if(getChild(1) instanceof Variable varIndex) {
                if(varIndex.isConstant())   //TODO case 1: constant index
                    return tupleType.getChild(0).getType();
            }
            return tupleType.getChild(0).getType(); //TODO case 2: all children are the same type
        }
        else if(parentType instanceof Sequence seqType)
            return seqType.getStoredType();
        else
            throw new Error("Can not index " + parentType);
    }

    public BasicType interpret() {
        //TODO non-numerical index
        return getChild(0).interpret().getChild(((Numerical)getChild(1).interpret()).asInt()).interpret();
    }
}
