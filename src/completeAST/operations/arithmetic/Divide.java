package completeAST.operations.arithmetic;

import derivedAST.FinalSyntaxNode;

public class Divide extends operations.arithmetic.ArithmeticInfix {
    public Divide(){}
    public Divide(FinalSyntaxNode origin, FinalSyntaxNode vector) {
        super(origin, vector);
    }

    public String getName() {
        return "div";
    }
}
