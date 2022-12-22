package AST.operations.arithmetic;

import AST.baseTypes.BasicType;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.abstractNode.SyntaxNode;

public class Negative extends ArithmeticOperator {
    public Negative(){}
    public Negative(SyntaxNode value) {
        addChild(value);
    }

    public String getName() {
        return "negative";
    }

    public BasicType interpretFloats(Numerical first, Numerical second) {
        return new Float(-first.asDouble());
    }

    public BasicType interpretInts(Numerical first, Numerical second) {
        return new Int(-first.asInt());
    }
}
