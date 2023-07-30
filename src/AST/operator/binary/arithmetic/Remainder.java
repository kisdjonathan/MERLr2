package AST.operator.binary.arithmetic;

import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.RemFloat;
import compiler.commands.arithmetic.RemInt;

import java.util.List;

public class Remainder extends ArithmeticOperator {
    public static final String literal = "rem";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            //TODO optimize
            (a,b)->a%b,
            (a,b)->a%b,
            RemInt::new,
            RemFloat::new
    );

    public String getName() {
        return literal;
    }
    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Remainder();
    }
}
