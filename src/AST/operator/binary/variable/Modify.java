package AST.operator.binary.variable;

import AST.abstractNode.BinaryOperator;
import AST.abstractNode.SyntaxNode;
import compiler.commands.arithmetic.AddFloat;
import compiler.commands.arithmetic.AddInt;

import java.util.List;

public class Modify extends AssignmentOperator {
    public static final String literal = "set";
    private static final List<Evaluation> evaluationList = initEvaluationList(false);

    public String getName() {
        return literal;
    }

    protected List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public SyntaxNode emptyClone() {
        return new Modify();
    }
}

