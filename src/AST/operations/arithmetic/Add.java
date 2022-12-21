package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;

public class Add extends ArithmeticInfix {
    public Add(){}
    public Add(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "add";
    }

    public Value interpret(Context context) {
        Value first = getOrigin().interpret(context);
        Value second = getOrigin().interpret(context);


    }
}
