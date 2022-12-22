package AST.operations.bitwise;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
import AST.baseTypes.Char;
import AST.operations.Operator;

public abstract class BitwiseOperator extends Operator {
    public BitwiseOperator() {}
    public BitwiseOperator(SyntaxNode a, SyntaxNode b) {
        addChild(a);
        addChild(b);
    }

    //TODO
    public BasicType getType() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ?  getChild(1).interpret() : null;
        if (first instanceof Bool != second instanceof Bool) {
            throw new Error("Unsupported arguments for " + getName() + " operator: \n\tfirst: " + first.getType() + "\n\tsecond:" + (second != null ? second.getType() : null));
        } else if (first instanceof Bool) {
            return new Bool();
        } else {
            return new Char();
        }
    }

    public BasicType interpret() {
        BasicType first = getChild(0).interpret();
        BasicType second = size() > 1 ?  getChild(1).interpret() : null;
        BasicType outType = getType();

        if (outType instanceof Bool) {
            return interpretBools((Bool) first, (Bool) second);
        } else {
            //TODO
            return interpretBytes((Char) first, (Char) second);
        }
    }

    protected abstract Bool interpretBools(Bool first, Bool second);

    protected abstract Char interpretBytes(Char first, Char second);

}
