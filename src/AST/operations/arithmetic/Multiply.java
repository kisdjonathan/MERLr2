package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;

public class Multiply extends ArithmeticOperator {
    public Multiply(){}
    public Multiply(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "mul";
    }

    public BasicType interpretFloats(Numerical first, Numerical second) {
        return new Float(first.asDouble() * second.asDouble());
    }

    public BasicType interpretInts(Numerical first, Numerical second) {
        return new Int(first.asInt() * second.asInt());
    }
}
