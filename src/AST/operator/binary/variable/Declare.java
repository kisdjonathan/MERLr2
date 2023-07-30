package AST.operator.binary.variable;

import AST.abstractNode.SyntaxNode;

import java.util.List;

public class Declare extends AssignmentOperator {
    public static final String literal = "decl";
    private static final List<Evaluation> evaluationList = initEvaluationList(true);

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
