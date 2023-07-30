package AST.operator.binary.arithmetic;

import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.AddFloat;
import compiler.commands.arithmetic.AddInt;
import compiler.commands.arithmetic.ModFloat;
import compiler.commands.arithmetic.ModInt;

import java.util.List;

public class Modulo extends ArithmeticOperator {
    public static final String literal = "mod";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            //TODO optimize
            Math::floorMod,
            (a,b)->{
                double c = a % b;
                return c < 0 ? c + b : c;
            },
            ModInt::new,
            ModFloat::new
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
