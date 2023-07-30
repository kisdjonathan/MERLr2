package AST.operator.binary.arithmetic;

import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.AddFloat;
import compiler.commands.arithmetic.AddInt;
import compiler.commands.arithmetic.SubFloat;
import compiler.commands.arithmetic.SubInt;
import type.Type;

import java.util.List;

public class Subtract extends ArithmeticOperator {
    public static final String literal = "sub";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            (a,b)->a-b,
            (a,b)->a-b,
            SubInt::new,
            SubFloat::new
    );

    public String getName() {
        return literal;
    }
    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Subtract();
    }
}
