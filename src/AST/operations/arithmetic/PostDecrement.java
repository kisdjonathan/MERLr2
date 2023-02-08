package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class PostDecrement extends Operator {

    public PostDecrement(){}

    public PostDecrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("postdec");
        setUnaryEvaluation("postdec",new Int(), new Int(), x -> {
            Int result = x.clone();
            x.setValue(x.asInt() - 1);
            return result;
        });
        setUnaryEvaluation("postdec",new Float(), new Float(), x -> {
            Float result = x.clone();
            x.setValue(x.asDouble() - 1);
            return result;
        });
    }

    @Override
    public SyntaxNode clone() {
        return new PostDecrement(getChild(0).clone());
    }

    @Override
    public String getName() {
        return "postdec";
    }
}
