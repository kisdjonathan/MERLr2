package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.operations.UnaryOperator;

public class PreDecrement extends UnaryOperator {

    public PreDecrement(){}

    public PreDecrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("predec");
        setEvaluation("predec",new Int(), new Int(), x -> {
            x.setValue(x.asInt() - 1);
            return x;
        });
        setEvaluation("predec",new Float(), new Float(), x -> {
            x.setValue(x.asDouble() - 1);
            return x;
        });
    }

    @Override
    public SyntaxNode clone() {
        return new PreDecrement(getChild(0).clone());
    }

    @Override
    public String getName() {
        return "predec";
    }
}
