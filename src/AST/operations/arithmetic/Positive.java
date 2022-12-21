package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;

public class Positive extends ArithmeticInfix {
    public static SyntaxNode positiveOf(SyntaxNode v) {
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

    public Positive(){}
    public Positive(SyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "positive";
    }
}
