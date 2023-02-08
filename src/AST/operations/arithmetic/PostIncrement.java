package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class PostIncrement extends Operator {

    public PostIncrement(){}

    public PostIncrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("postinc");
        setUnaryEvaluation("postinc",new Int(), new Int(), x -> {
            Int result = x.clone();
            x.setValue(x.asInt() + 1);
            return result;
        });
        setUnaryEvaluation("postinc",new Float(), new Float(), x -> {
            Float result = x.clone();
            x.setValue(x.asDouble() + 1);
            return result;
        });
    }

    @Override
    public SyntaxNode clone() {
        return new PostIncrement(getChild(0).clone());
    }

    @Override
    public String getName() {
        return "postinc";
    }
}
