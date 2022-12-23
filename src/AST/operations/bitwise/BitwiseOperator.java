package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.operations.Operator;

public abstract class BitwiseOperator extends Operator {
    public BitwiseOperator() {}
    public BitwiseOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    //TODO
    public BasicType getType() {
        BasicType first = getChild(0).getType();
        BasicType second = size() > 1 ?  getChild(1).getType() : null;
        if (first instanceof Bool && second instanceof Bool) {
            return new Bool();
        } else if (first instanceof Int && second instanceof Int) {
            return new Int();
        } else {
            throw new Error("Unsupported arguments for " + getName() + " operator: \n\tfirst: " + first + "\n\tsecond:" + second);
        }
    }

    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ?  getChild(1).interpret() : null;
        BasicType outType = getType();

        if (outType instanceof Bool) {
            return interpretBools((Bool) first, (Bool) second);
        } else if (outType instanceof Int){
            return interpretBytes((Int) first, (Int) second);
        } else {
            //TODO
            throw new Error("invalid types for " + getName());
        }
    }

    protected abstract Bool interpretBools(Bool first, Bool second);

    protected abstract Int interpretBytes(Int first, Int second);

}
