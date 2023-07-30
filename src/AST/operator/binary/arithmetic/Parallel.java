package AST.operator.binary.arithmetic;

import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.ParFloat;
import compiler.commands.arithmetic.ParInt;

import java.util.List;

public class Parallel extends ArithmeticOperator {
    public static final String literal = "parallel";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            Math::floorMod,
            (a,b)->{
                double c = a % b;
                return c < 0 ? c + b : c;
            },
            ParInt::new,
            ParFloat::new
    );

    public String getName() {
        return literal;
    }
    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Parallel();
    }
}
