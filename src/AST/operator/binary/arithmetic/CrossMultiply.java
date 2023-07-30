package AST.operator.binary.arithmetic;

import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.MulFloat;
import compiler.commands.arithmetic.MulInt;

import java.util.List;

public class CrossMultiply extends ArithmeticOperator {
    public static final String literal = "cross";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            (a,b)->a*b,
            (a,b)->a*b,
            MulInt::new,
            MulFloat::new
    );

    public String getName() {
        return literal;
    }
    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new CrossMultiply();
    }
}
