package AST.operator.binary.arithmetic;

import AST.abstractNode.Operator;
import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.AddFloat;
import compiler.commands.arithmetic.AddInt;
import compiler.commands.arithmetic.DivFloat;
import compiler.commands.arithmetic.DivInt;
import type.Type;

import java.util.List;

public class Divide extends ArithmeticOperator {
    public static final String literal = "div";
    private static final List<Operator.Evaluation> evaluationList = initEvaluationList(
            (a,b)->a/b,
            (a,b)->a/b,
            DivInt::new,
            DivFloat::new
    );

    public String getName() {
        return literal;
    }

    protected List<Operator.Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public Type getType() {
        return null;
    }

    public SyntaxNode emptyClone() {
        return new Divide();
    }
}
