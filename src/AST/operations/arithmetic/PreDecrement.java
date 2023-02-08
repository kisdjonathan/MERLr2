package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class PreDecrement extends Operator {

    public PreDecrement(){}

    public PreDecrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("predec");
        setUnaryEvaluation("predec",new Int(), new Int(), x -> {
            x.setValue(x.asInt() - 1);
            return x;
        });
        setUnaryEvaluation("predec",new Float(), new Float(), x -> {
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
