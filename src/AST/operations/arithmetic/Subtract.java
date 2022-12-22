package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.baseTypes.Float;

public class Subtract extends ArithmeticOperator {
    public Subtract(){}
    public Subtract(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "sub";
    }

    public Float interpretFloats(Numerical first, Numerical second) {
        return new Float(first.asDouble() - second.asDouble());
    }

    public Int interpretInts(Numerical first, Numerical second) {
        return new Int(first.asInt() - second.asInt());
    }
}
