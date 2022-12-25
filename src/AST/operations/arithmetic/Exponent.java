package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;

public class Exponent extends ArithmeticOperator {
    public Exponent(){}
    public Exponent(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "exp";
    }

    public Exponent clone() {
        return new Exponent(getChild(0).clone(), getChild(1).clone());
    }

    public Float interpretFloats(Numerical first, Numerical second) {
        return new Float(Math.pow(first.asDouble(), second.asDouble()));
    }

    public Int interpretInts(Numerical first, Numerical second) {
        return new Int((int) Math.pow(first.asInt(), second.asInt()));
    }
}
