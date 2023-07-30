package AST.operator.binary.arithmetic;

import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.ModFloat;
import compiler.commands.arithmetic.ModInt;
import compiler.commands.arithmetic.RoundFloat;
import compiler.commands.arithmetic.RoundInt;

import java.util.List;

public class Round extends ArithmeticOperator {
    public static final String literal = "round";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            //TODO optimize
            (a,b)-> (a/b)*b,
            (a,b)-> Math.round(a/b)*b,
            RoundInt::new,
            RoundFloat::new
    );

    public String getName() {
        return literal;
    }
    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Modulo();
    }
}