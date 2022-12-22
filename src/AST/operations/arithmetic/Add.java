package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;

public class Add extends ArithmeticOperator {
    public Add(){}
    public Add(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "add";
    }

    public Float interpretFloats(Numerical first, Numerical second) {
        return new Float(first.asDouble() + second.asDouble());
    }

    public Int interpretInts(Numerical first, Numerical second) {
        return new Int(first.asInt() + second.asInt());
    }

}
