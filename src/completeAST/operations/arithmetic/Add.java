package completeAST.operations.arithmetic;

import Interpreter.Context;
import Interpreter.Value;
import derivedAST.FinalSyntaxNode;

public class Add extends operations.arithmetic.ArithmeticInfix {
    public Add(){}
    public Add(FinalSyntaxNode origin, FinalSyntaxNode vector) {
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
