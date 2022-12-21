package AST.operations.arithmetic;

import baseTypes.*;
import data.Usage;
import AST.abstractNode.SyntaxNode;

public class Negative extends ArithmeticInfix {
    public static SyntaxNode negativeOf(SyntaxNode v) {
        if(v.equals(Usage.TYPE) && ((BasicType)v).isNumeric()) {
            Numerical ret;
            if(v instanceof Int ival)
                ret = new Int(-ival.getValue());
            else if(v instanceof Char cval)
                ret = new Char((short)(-cval.getValue()));
            else if(v instanceof Float fval)
                ret = new Float((float)(-fval.getValue()));
            else
                throw new Error("Invalid numerical type " + v);

            ret.setLong(((Numerical) v).isLong());
            return ret;
        }
        return new Negative(v);
    }

    public Negative(){}
    public Negative(SyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "negative";
    }
}
