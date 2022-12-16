package completeAST.operations.arithmetic;

import derivedAST.FinalSyntaxNode;

public class Subtract extends operations.arithmetic.ArithmeticInfix {
    public Subtract(){}
    public Subtract(FinalSyntaxNode origin, FinalSyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "sub";
    }
}
