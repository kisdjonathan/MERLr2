package AST.operator.binary.arithmetic;

import AST.abstractNode.Operator;
import AST.abstractNode.SyntaxNode;
import compiler.commands.Move;
import compiler.commands.Repeat;
import compiler.commands.arithmetic.*;
import compiler.components.Register;
import type.Type;
import type.numerical.Int;

import java.util.List;

public class Exponent extends ArithmeticOperator {
    public static final String literal = "pow";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            //TODO optimize
            (a,b)-> (int) Math.pow(a,b),
            Math::pow,
            PowInt::new,
            PowFloat::new
    );

    public String getName() {
        return literal;
    }

    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Exponent();
    }
}
