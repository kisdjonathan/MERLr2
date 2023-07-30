package AST.operator.binary.arithmetic;

import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.PowFloat;
import compiler.commands.arithmetic.PowInt;
import compiler.commands.arithmetic.RootFloat;
import compiler.commands.arithmetic.RootInt;

import java.util.List;

public class Root extends ArithmeticOperator {
    public static final String literal = "root";
    private static final List<Evaluation> evaluationList = initEvaluationList(
            //TODO optimize
            (a,b)-> (int) Math.pow(a,1./b),
            (a,b)-> Math.pow(a,1./b),
            RootInt::new,
            RootFloat::new
    );

    public String getName() {
        return literal;
    }

    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Root();
    }
}