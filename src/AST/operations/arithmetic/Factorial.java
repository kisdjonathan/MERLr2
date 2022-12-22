package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;

public class Factorial extends ArithmeticOperator {
    public Factorial(){}
    public Factorial(SyntaxNode value) {
        addChild(value);
    }

    public String getName() {
        return "factorial";
    }

    public Float interpretFloats(Numerical first, Numerical second) {
        return null;    //TODO
    }

    public Int interpretInts(Numerical first, Numerical second) {
        int stop = first.asInt();
        if(stop < 0)
            throw new Error("NaN");

        int ret = stop;
        for(int i = 1; i < stop; ++i)
            ret *= i;
        return new Int(ret);
    }
}
